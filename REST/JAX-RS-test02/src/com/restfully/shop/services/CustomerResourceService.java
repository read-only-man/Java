package com.restfully.shop.services;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import org.apache.commons.lang3.StringUtils;

import com.restfully.shop.domain.Customer;
import com.restfully.shop.util.CustomerUtil;

@Path("/customers")
public class CustomerResourceService implements CustomerResorce {

    private Map<Integer, Customer> custmerDB = new ConcurrentHashMap<Integer, Customer>();

    private AtomicInteger          idCounter = new AtomicInteger(0);

    @Override
    public Response createCustomer(InputStream is) {
        Customer customer = CustomerUtil.readCustomer(is);
        customer.setId(idCounter.incrementAndGet());
        custmerDB.put(customer.getId(), customer);
        System.out.println("Created customer " + String.format("%6d", customer.getId()));
        return Response.created(URI.create("/customers/" + customer.getId())).build();
    }

    @Override
    public StreamingOutput getCustomer(int id) {
        final Customer customer = custmerDB.get(id);
        if (customer == null)
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        return new StreamingOutput() {
            @Override
            public void write(OutputStream os) throws IOException, WebApplicationException {
                CustomerUtil.outputCustomer(os, customer);
            }
        };
    }

    @Override
    public StreamingOutput getCustomerByName(String first, String last) {
        for (Customer customer : custmerDB.values()) {
            if (StringUtils.equals(first, customer.getFirstName()) && StringUtils.equals(last, customer.getLastName()))
                return new StreamingOutput() {
                    @Override
                    public void write(OutputStream os) throws IOException, WebApplicationException {
                        CustomerUtil.outputCustomer(os, customer);
                    }
                };
        }
        throw new WebApplicationException(Response.Status.NOT_FOUND);
    }

    @Override
    public void updateCustomer(int id, InputStream is) {
        Customer update = CustomerUtil.readCustomer(is);
        Customer current = custmerDB.get(id);
        if (current == null)
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        current.update(update);
    }

    @Override
    public void patchCustomer(int id, InputStream is) {
        this.updateCustomer(id, is);
    }
}
