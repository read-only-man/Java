package com.restfully.shop.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomUtils;

public class ServiceUtil {
	protected ServiceUtil() {
		;
	}

	protected static Object anyItemFromList(List<?> list) {
		return list.get(RandomUtils.nextInt(0, list.size() - 1));
	}

	protected static Object anyItemFromMap(Map<?, ?> map) {
		List<Object> list = new ArrayList<Object>();
		list.addAll(map.values());
		return list.get(RandomUtils.nextInt(0, list.size() - 1));
	}
}
