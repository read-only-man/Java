package com.restfully.shop.services;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.websocket.server.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import com.restfully.shop.domain.Link;
import com.restfully.shop.domain.Order;
import com.restfully.shop.domain.Orders;

@Path("/orders")
public class OrderResource {

	public static Map<Integer, Order> orderDB = new ConcurrentHashMap<Integer, Order>() {
		private static final long serialVersionUID = -8253898102976026669L;

		{
			for (int i = 1; i < 10; i++) {
				put(i, OrderUtil.generateOrder(i));
			}
		}
	};
	private static AtomicInteger idCounter = new AtomicInteger(10);

	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public Response createOrder(Order order, @Context UriInfo uriInfo) {
		order.setId(idCounter.incrementAndGet());
		orderDB.put(order.getId(), order);
		System.out.println("Cteated order " + order.getId());
		return Response.created(uriInfo.getAbsolutePathBuilder().path(String.valueOf(order.getId())).build()).build();
	}

	@POST
	@Path("/purge")
	public void purgeOrders() {
		synchronized (orderDB) {
			List<Order> orders = new ArrayList<Order>();
			orders.addAll(orderDB.values());
			for (Order order : orders) {
				if (order.isCancelled()) {
					orderDB.remove(order).getId();
				}
			}
		}
	}

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getOrders(@QueryParam("start") int start, @QueryParam("size") @DefaultValue("2") int size,
			@Context UriInfo uriInfo) {
		UriBuilder builder = uriInfo.getAbsolutePathBuilder().queryParam("start", "{start}").queryParam("size",
				"{size}");

		List<Order> list = new ArrayList<Order>();
		List<Link> links = new ArrayList<Link>();

		for (int i = start; i < orderDB.size() - 1 && start + size > i; i++) {
			list.add(orderDB.get(i + 1));
		}

		if (start + size < orderDB.size()) {
			URI nextURI = builder.clone().build(start + size, size);
			links.add(new Link("next", nextURI.toString(), MediaType.APPLICATION_XML));
		}
		if (start > 0) {
			int prev = start - size > 0 ? start - size : 0;
			URI prevURI = builder.clone().build(prev, size);
			links.add(new Link("previous", prevURI.toString(), MediaType.APPLICATION_XML));
		}

		Orders orders = new Orders();
		orders.setOrders(list);
		orders.setLinks(links);

		ResponseBuilder responseBuilder = Response.ok(orders);
		responseBuilder = getPurgeBuilder(uriInfo, responseBuilder);
		return responseBuilder.build();
	}

	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getOrder(@PathParam("id") int id, @Context UriInfo uriInfo) {

		Order order = orderDB.get(id);
		if (order == null)
			throw new WebApplicationException(Response.Status.NOT_FOUND);

		Response.ResponseBuilder builder = Response.ok(order);
		if (order.isCancelled())
			builder = getCancelBuilder(uriInfo, builder);

		return builder.build();
	}

	@HEAD
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getOrderHeaders(@PathParam("id") int id, @Context UriInfo uriInfo) {
		Order order = orderDB.get(id);
		if (order == null)
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		Response.ResponseBuilder builder = Response.ok();
		if (order.isCancelled())
			builder = getCancelBuilder(uriInfo, builder);

		return builder.build();
	}

	@POST
	@Path("{id}/cancel")
	public void cancelOrder(@PathParam("id") int id) {
		Order order = orderDB.get(id);
		if (order == null)
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		order.setCancelled(true);
	}

	private ResponseBuilder getCancelBuilder(UriInfo uriInfo, ResponseBuilder builder) {
		UriBuilder absolute = uriInfo.getAbsolutePathBuilder();
		String cancelUrl = absolute.clone().path("cancel").build().toString();
		return builder.clone().header("Link", new Link("cancel", cancelUrl, null));
	}

	private ResponseBuilder getPurgeBuilder(UriInfo uriInfo, ResponseBuilder responseBuilder) {
		UriBuilder absolute = uriInfo.getAbsolutePathBuilder();
		String purgeUri = absolute.clone().path("purge").build().toString();
		return responseBuilder.clone().header("Link", new Link("purge", purgeUri, null));
	}
}
