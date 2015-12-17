package com.resfully.car.services;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.ws.rs.GET;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.StreamingOutput;
import javax.ws.rs.core.UriInfo;

@Path("/cars")
public class CarResources {
	public static enum Color {
		red, blue, green, yellow, black, white
	}

	@GET
	@Path("/test")
	@Produces("text/plain")
	public StreamingOutput getDefault() {
		return new StreamingOutput() {
			@Override
			public void write(OutputStream os) throws IOException, WebApplicationException {
				PrintStream writer = new PrintStream(os);
				writer.print("default");
			}
		};
	}

	@GET
	@Path("/matrix/{make}/{model}/{year}")
	@Produces("text/plain")
	public StreamingOutput getFromMatrixParam(@PathParam("make") String make, @PathParam("model") PathSegment car,
			@MatrixParam("c") Color color, @PathParam("year") String year) {
		return new StreamingOutput() {

			// example /cars/matrix/mercedes/e55;c=black/2006
			@Override
			public void write(OutputStream os) throws IOException, WebApplicationException {
				PrintStream writer = new PrintStream(os);
				writer.print("A " + color + " " + year + " " + make + " " + car.getPath());
			}
		};
	}

	@GET
	@Path("/segments/{make}/{model}/{year}")
	@Produces("text/plain")
	public StreamingOutput getFromMultiplueSegments(@PathParam("make") String make, @PathParam("model") PathSegment car,
			@PathParam("year") String year) {
		String color = car.getMatrixParameters().getFirst("c");
		return new StreamingOutput() {

			// example /cars/matrix/mercedes/e55;c=black/2006
			@Override
			public void write(OutputStream os) throws IOException, WebApplicationException {
				PrintStream writer = new PrintStream(os);
				writer.print("A " + color + " " + year + " " + make + " " + car.getPath());
			}
		};
	}

	@GET
	@Path("/uriinfo/{make}/{model}/{year}")
	@Produces("text/plain")
	public StreamingOutput getFromUriInfo(@Context UriInfo info) {
		String make = info.getPathParameters().getFirst("make");
		String year = info.getPathParameters().getFirst("year");
		PathSegment car = info.getPathSegments().get(3);
		String color = car.getMatrixParameters().getFirst("c");
		return new StreamingOutput() {

			// example /cars/matrix/mercedes/e55;c=black/2006
			@Override
			public void write(OutputStream os) throws IOException, WebApplicationException {
				PrintStream writer = new PrintStream(os);
				writer.print("A " + color + " " + year + " " + make + " " + car.getPath() + "\r\n");
				StringBuilder buf = new StringBuilder("Pathsegs : ");
				for (PathSegment pth : info.getPathSegments()) {
					buf.append(pth.getPath() + " ");
				}
				buf.deleteCharAt(buf.length() - 1);
				writer.println(buf);
			}
		};
	}
}
