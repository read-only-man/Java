package com.restfully.shop.domain;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "items")
public class Items implements Serializable {

	private static final long serialVersionUID = 5527429097827222281L;
	private List<Item> items;

	@XmlElementRef
	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

}
