package com.restfully.shop.services;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

public class ShoppingApplication extends Application {
    private Set<Object>   singletons = new HashSet<Object>();
    private Set<Class<?>> empty      = new HashSet<Class<?>>();

    public ShoppingApplication() {
        singletons.add(new CustomerResourceService());
    }

    @Override
    public Set<Class<?>> getClasses() {
        return this.empty;
    }

    @Override
    public Set<Object> getSingletons() {
        return this.singletons;
    }
}
