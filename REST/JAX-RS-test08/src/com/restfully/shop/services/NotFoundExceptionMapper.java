package com.restfully.shop.services;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

	@Override
	public Response toResponse(NotFoundException ex) {
		return Response.status(Response.Status.NOT_FOUND).entity(ex.getMessage()).type(MediaType.TEXT_PLAIN).build();
	}

}
