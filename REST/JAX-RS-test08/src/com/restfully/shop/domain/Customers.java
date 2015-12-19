package com.restfully.shop.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@XmlRootElement(name = "customers")
public class Customers implements Serializable {

	private static final long serialVersionUID = -6949295205108620004L;

	protected Collection<Customer> customers;
	protected List<Link> links;

	@XmlElementRef
	public Collection<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(Collection<Customer> customers) {
		this.customers = customers;
	}

	@XmlElementRef
	public List<Link> getLinks() {
		return links;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}

	@XmlTransient
	@JsonIgnore
	public String getNext() {
		if (links != null)
			for (Link link : links)
				if ("next".equals(link.getRelationship()))
					return link.getHref();
		return null;
	}

	@XmlTransient
	@JsonIgnore
	public String getPrevious() {
		if (links != null)
			for (Link link : links)
				if ("previous".equals(link.getRelationship()))
					return link.getHref();
		return null;
	}
}
