package com.restfully.shop.domain;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

import com.sun.xml.internal.txw2.annotation.XmlElement;

@XmlRootElement(name = "order")
public class Order implements Serializable {

	private static final long serialVersionUID = -4070732869108726456L;

	private int id;
	private boolean cancelled;
	private String amount;
	private Customer customer;
	private Items lineItems;

	@XmlAttribute
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@XmlElement
	public boolean isCancelled() {
		return cancelled;
	}

	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	@XmlElement
	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	@XmlElementRef
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@XmlElementRef
	public Items getLineItems() {
		return lineItems;
	}

	public void setLineItems(Items lineItems) {
		this.lineItems = lineItems;
	}

}
