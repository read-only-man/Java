package com.restfully.shop.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomUtils;

import com.restfully.shop.domain.Customer;

public final class CustomerUtil {
	private CustomerUtil() {
		;
	}

	private static final String alphabets = "abcdefghijklmnopqrstuvwxyz";

	// test data
	private static final List<String> firstNames = new ArrayList<String>() {

		private static final long serialVersionUID = -3057340913492156381L;

		{
			for (int i = 0; i < 20; i++) {
				StringBuilder buf = new StringBuilder();
				int pos = RandomUtils.nextInt(0, alphabets.length() - 1);
				int step = RandomUtils.nextInt(0, 10);

				buf.append(Character.toUpperCase(alphabets.charAt(pos)));

				do {
					pos = (pos + step) % (alphabets.length() - 1);
					buf.append(alphabets.charAt(pos));
				} while (RandomUtils.nextInt(0, 2) == 0);
				add(buf.toString());
			}
		}
	};
	// test data
	private static final List<String> lastNames = new ArrayList<String>() {

		private static final long serialVersionUID = -6844892103906564376L;

		{
			for (int i = 0; i < 20; i++) {
				StringBuilder buf = new StringBuilder();
				int pos = RandomUtils.nextInt(0, alphabets.length() - 1);
				int step = RandomUtils.nextInt(0, 10);

				buf.append(Character.toUpperCase(alphabets.charAt(pos)));

				do {
					pos = (pos + step) % (alphabets.length() - 1);
					buf.append(alphabets.charAt(pos));
				} while (RandomUtils.nextInt(0, 2) == 0);
				add(buf.toString());
			}
		}
	};
	// test data
	private static final List<String> streets = new ArrayList<String>() {

		private static final long serialVersionUID = 380087135961996089L;

		{
			for (int i = 0; i < 20; i++) {
				StringBuilder buf = new StringBuilder();
				int pos = RandomUtils.nextInt(0, alphabets.length() - 1);
				int step = RandomUtils.nextInt(0, 10);
				do {
					pos = (pos + step) % (alphabets.length() - 1);
					buf.append(alphabets.charAt(pos));
				} while (RandomUtils.nextInt(0, 3) != 0);
				add(buf.toString());
			}
		}
	};
	// test data
	private static final List<String> cities = new ArrayList<String>() {

		private static final long serialVersionUID = 8946420511362048903L;

		{
			for (int i = 0; i < 20; i++) {
				StringBuilder buf = new StringBuilder();
				int pos = RandomUtils.nextInt(0, alphabets.length() - 1);
				int step = RandomUtils.nextInt(0, 10);
				do {
					pos = (pos + step) % (alphabets.length() - 1);
					buf.append(alphabets.charAt(pos));
				} while (RandomUtils.nextInt(0, 3) != 0);
				add(buf.toString());
			}
		}
	};
	// test data
	private static final List<String> zips = new ArrayList<String>() {

		private static final long serialVersionUID = -2179698734964221214L;

		{
			for (int i = 0; i < 20; i++) {
				StringBuilder buf = new StringBuilder();
				buf.append(RandomUtils.nextInt(1, 10));
				for (int j = 0; j < 6; j++) {
					buf.append(RandomUtils.nextInt(0, 10));
					if (j == 1)
						buf.append("-");
				}
				add(buf.toString());
			}
		}
	};
	// test data
	private static final List<String> countrys = new ArrayList<String>() {

		private static final long serialVersionUID = -4427664463797838376L;

		{
			add("USA");
			add("JPN");
			add("CH");
			add("RUS");
			add("IND");
			add("EN");
			add("FH");
		}
	};

	// generate test data
	public static Customer generateCustomer(int i) {
		Customer ret = new Customer();
		ret.setId(i);
		ret.setFirstName((String) anyItemFromList(firstNames));
		ret.setLastName((String) anyItemFromList(lastNames));
		ret.setStreet((String) anyItemFromList(streets));
		ret.setCity((String) anyItemFromList(cities));
		ret.setZip((String) anyItemFromList(zips));
		ret.setCountry((String) anyItemFromList(countrys));
		return ret;
	}

	private static Object anyItemFromList(List<?> list) {
		return list.get(RandomUtils.nextInt(0, list.size() - 1));
	}

}
