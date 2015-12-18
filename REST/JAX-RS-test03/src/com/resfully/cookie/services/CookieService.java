package com.resfully.cookie.services;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.Date;

import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import javax.ws.rs.core.UriInfo;

@Path("/cookie")
public class CookieService {

	@GET
	@Path("/set")
	@Produces("text/html")
	public Response setCookie(@Context UriInfo info) {
		String output = "<a href='./get'>link</a>";
		return Response.created(URI.create(info.getPath())).entity(output)
				.cookie(new NewCookie("date-time", new Date().toString())).build();
	}

	@GET
	@Path("/get")
	@Produces(MediaType.TEXT_PLAIN)
	public StreamingOutput getCookie(@CookieParam("date-time") String time) {
		return new StreamingOutput() {

			@Override
			public void write(OutputStream os) throws IOException, WebApplicationException {
				os.write(time.getBytes());
			}
		};

	}
}
