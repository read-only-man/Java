package com.restfully.shop.domain;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.sun.xml.internal.txw2.annotation.XmlElement;

@XmlRootElement(name = "item")
public class Item implements Serializable {

	private static final long serialVersionUID = -2420289006356791154L;

	private String cost;
	private String product;

	public Item() {
		;
	}

	public Item(String cost, String product) {
		this.cost = cost;
		this.product = product;
	}

	@XmlElement
	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	@XmlElement
	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

}
