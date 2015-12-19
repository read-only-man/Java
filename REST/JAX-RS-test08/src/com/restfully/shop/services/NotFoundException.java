package com.restfully.shop.services;

public class NotFoundException extends RuntimeException {

	private static final long serialVersionUID = -2906324786840234710L;

	public NotFoundException(String s) {
		super(s);
	}

}
