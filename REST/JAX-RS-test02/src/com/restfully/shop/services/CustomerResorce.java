package com.restfully.shop.services;

import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import org.ieft.annotations.PATCH;

public interface CustomerResorce {

    @POST
    @Consumes("application/xml")
    public Response createCustomer(InputStream is);

    @GET
    @Path("{id:\\d+}")
    @Produces("application/xml")
    public StreamingOutput getCustomer(@PathParam("id") int id);

    @GET
    @Path("{first : [A-z]+}-{last : [A-z]+}")
    @Produces("application/xml")
    public StreamingOutput getCustomerByName(@PathParam("first") String first, @PathParam("last") String last);

    @PUT
    @Path("{id:\\d+}")
    @Consumes("application/xml")
    public void updateCustomer(@PathParam("id") int id, InputStream is);

    @PATCH
    @Path("{id:\\d+}")
    @Consumes("application/xml")
    public void patchCustomer(@PathParam("id") int id, InputStream is);
}
