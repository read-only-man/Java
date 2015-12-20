package com.restfully.shop.services;

import javax.ws.rs.HEAD;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import com.restfully.shop.domain.Link;

@Path("/shop")
public class StoreResource {

	@HEAD
	public Response head(@Context UriInfo uriInfo) {
		UriBuilder absolute = uriInfo.getBaseUriBuilder();

		String customerUrl = absolute.clone().path("customers").build().toString();
		String orderUrl = absolute.clone().path("orders").build().toString();

		Response.ResponseBuilder builder = Response.ok();
		builder.header("Link", new Link("customers", customerUrl, MediaType.APPLICATION_XML));
		builder.header("Link", new Link("orders", orderUrl, MediaType.APPLICATION_XML));

		return builder.build();
	}
}
