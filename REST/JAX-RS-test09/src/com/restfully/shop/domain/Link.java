package com.restfully.shop.domain;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

	/**
	 * リンクヘッダ用.
	 * 
	 * @return リンクヘッダ表現
	 */
	@Override
	public String toString() {
		StringBuilder buf = new StringBuilder("<");
		buf.append(href).append(">; rel=").append(relationship);
		if (type != null)
			buf.append("; type=").append(type);
		return buf.toString();
	}

	private static final Pattern parse = Pattern.compile("<(.+)>\\s*;\\s*(.+)");

	/**
	 * Linkのアンマーシャラー
	 * 
	 * @param str
	 *            このオブジェクトの文字列表現
	 * @return Linkオブジェクト
	 */
	public static Link valueOf(String str) {

		Matcher matcher = parse.matcher(str);
		if (!matcher.matches())
			throw new RuntimeException("Failed to parse link : " + str);
		Link link = new Link();
		link.href = matcher.group(1);
		String[] props = matcher.group(2).split(";");
		for (String prop : props) {
			String[] keyVal = prop.split("=");
			String key = keyVal[0];
			String val = keyVal[1];

			switch (key) {
			case "rel":
				link.relationship = val;
				break;
			case "type":
				link.type = val;
				break;
			default:
				break;
			}
		}
		return link;
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
