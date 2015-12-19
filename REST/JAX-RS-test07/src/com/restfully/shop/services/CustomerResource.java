package com.restfully.shop.services;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.restfully.shop.domain.Customer;

@Path("/customers")
public class CustomerResource {

	private static Map<Integer, Customer> customerDB = new ConcurrentHashMap<Integer, Customer>();

	private static AtomicInteger idCounter = new AtomicInteger(1);

	@GET
	@Path("/default")
	public String getDefault() {
		return "test";
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
