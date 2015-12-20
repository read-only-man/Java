package com.restfully.shop.services;

import java.math.BigDecimal;

import com.restfully.shop.domain.Customer;
import com.restfully.shop.domain.Item;
import com.restfully.shop.domain.Items;
import com.restfully.shop.domain.Order;

public class OrderUtil extends ServiceUtil {
	private OrderUtil() {
		;
	}

	public static Order generateOrder(int i) {
		Order order = new Order();
		order.setId(i);
		order.setCustomer((Customer) anyItemFromMap(CustomerResource.customerDB));
		order.setCancelled(false);
		order.setLineItems(ItemUtil.generateItems());
		order.setAmount(OrderUtil.calcAmount(order.getLineItems()));

		return order;
	}

	private static String calcAmount(Items lineItems) {

		BigDecimal amount = new BigDecimal(0);
		for (Item item : lineItems.getItems()) {
			amount = amount.add(new BigDecimal(item.getCost().substring(1)));
		}

		return amount.toString();
	}
}
