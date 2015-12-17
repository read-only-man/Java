package com.resfully.customer.services;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

@Path("/customers")
public class CustomerResources {

	private static List<String> customerDB = new CopyOnWriteArrayList<String>() {

		private static final long serialVersionUID = -7972159289452119727L;

		{
			add(0, "Hoge");
			add(1, "Sccot");
			add(2, "Tiger");
			add(3, "Bob");
			add(4, "Steave");
			add(5, "John");
			add(6, "AAA");
			add(7, "BBB");
		}
	};

	@GET
	@Produces("text/plain")
	public StreamingOutput getCustomers(@QueryParam("start") @DefaultValue("0") int start,
			@QueryParam("size") @DefaultValue("2") int size) {
		StringBuilder buf = new StringBuilder("reslut:\r\n");
		if (customerDB.size() < start + size)
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		List<String> result = customerDB.subList(start, start + size);
		for (String customer : result)
			buf.append(customer + "\r\n");
		buf.deleteCharAt(buf.length() - 1);
		return new StreamingOutput() {

			@Override
			public void write(OutputStream os) throws IOException, WebApplicationException {
				PrintStream writer = new PrintStream(os);
				writer.print(buf);
			}
		};
	}
}
