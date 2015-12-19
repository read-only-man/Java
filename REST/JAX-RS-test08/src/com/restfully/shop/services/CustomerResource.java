package com.restfully.shop.services;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import com.restfully.shop.domain.Customer;
import com.restfully.shop.domain.Customers;
import com.restfully.shop.domain.Link;

@Path("/customers")
public class CustomerResource {

	private static Map<Integer, Customer> customerDB = new ConcurrentHashMap<Integer, Customer>() {
		private static final long serialVersionUID = -2966717504358472190L;

		// テスト用の初期データ
		{
			put(1, CustomerUtil.generateCustomer(1));
			put(2, CustomerUtil.generateCustomer(2));
			put(3, CustomerUtil.generateCustomer(3));
			put(4, CustomerUtil.generateCustomer(4));
			put(5, CustomerUtil.generateCustomer(5));
			put(6, CustomerUtil.generateCustomer(6));
			put(7, CustomerUtil.generateCustomer(7));
			put(8, CustomerUtil.generateCustomer(8));
		}
	};
	private static AtomicInteger idCounter = new AtomicInteger(9);

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Customers getCustomers(@QueryParam("start") int start, @QueryParam("size") @DefaultValue("2") int size,
			@Context UriInfo uriinfo) {
		UriBuilder builder = uriinfo.getAbsolutePathBuilder().queryParam("start", "{start}").queryParam("size",
				"{size}");

		List<Customer> list = new ArrayList<Customer>();
		List<Link> links = new ArrayList<Link>();

		for (int i = start; i < customerDB.size() - 1 && start + size > i; i++) {
			list.add(customerDB.get(i + 1));
		}

		if (start + size < customerDB.size()) {
			URI nextURI = builder.clone().build(start + size, size);
			links.add(new Link("next", nextURI.toString(), MediaType.APPLICATION_XML));
		}
		if (start > 0) {
			int prev = start - size > 0 ? start - size : 0;
			URI prevURI = builder.clone().build(prev, size);
			links.add(new Link("previous", prevURI.toString(), MediaType.APPLICATION_XML));
		}

		Customers customers = new Customers();
		customers.setCustomers(list);
		customers.setLinks(links);
		return customers;
	}

	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public Response createCustomer(Customer customer) {
		customer.setId(idCounter.getAndIncrement());
		customerDB.put(customer.getId(), customer);
		System.out.println("create customer " + String.format("%4d", customer.getId()));
		return Response.created(URI.create("/customers/" + customer.getId())).build();
	}

	@POST
	@Consumes("application/x-java-serualized-object")
	public Response createCustomerFromObject(Customer customer) {
		customer.setId(idCounter.getAndIncrement());
		customerDB.put(customer.getId(), customer);
		System.out.println("create customer " + String.format("%4d", customer.getId()));
		System.out.println(customer);
		return Response.created(URI.create("/customers/" + customer.getId())).build();
	}

	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Customer getCustomer(@PathParam("id") int id) {
		Customer ret = customerDB.get(id);
		if (ret == null)
			throw new NotFoundException("Could not find customer " + id);
		return ret;
	}

	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_XML)
	public void updateCustomer(@PathParam("id") int id, Customer update) {
		Customer target = customerDB.get(id);
		if (target == null)
			throw new NotFoundException("Could not find customer " + id);
		target.setCity(update.getCity());
		target.setCountry(update.getCountry());
		target.setFirstName(update.getFirstName());
		target.setLastName(update.getLastName());
		target.setState(update.getState());
		target.setStreet(update.getStreet());
		target.setZip(update.getZip());
	}
}
