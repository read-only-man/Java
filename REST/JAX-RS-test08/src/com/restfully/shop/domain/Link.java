package com.restfully.shop.domain;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "link")
public class Link implements Serializable {

	private static final long serialVersionUID = 7923742245150786779L;

	public Link() {
		;
	}

	public Link(String relationship, String href, String type) {
		this.relationship = relationship;
		this.href = href;
		this.type = type;
	}

	private String relationship;
	private String href;
	private String type;

	@XmlAttribute(name = "rel")
	public String getRelationship() {
		return relationship;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}

	@XmlAttribute
	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	@XmlAttribute
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
