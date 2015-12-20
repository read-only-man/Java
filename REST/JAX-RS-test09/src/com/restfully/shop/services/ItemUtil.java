package com.restfully.shop.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomUtils;

import com.restfully.shop.domain.Item;
import com.restfully.shop.domain.Items;

public class ItemUtil extends ServiceUtil {

	private ItemUtil() {
		;
	}

	// test data
	private static List<Item> testItems = new ArrayList<Item>() {
		private static final long serialVersionUID = 8450439556683624516L;

		{
			add(new Item("$100", "RassberryPi"));
			add(new Item("$399", "iPhone"));
			add(new Item("$120", "Nexsus7"));
			add(new Item("$170", "Kindle voyage"));
			add(new Item("$600", "Mac book pro"));
			add(new Item("$600", "Surface pro4"));
			add(new Item("$100", "iPod nano"));
			add(new Item("$200", "unKnown"));
		}
	};

	public static Items generateItems() {

		List<Item> items = new ArrayList<Item>();

		int itemNum = RandomUtils.nextInt(1, 6);

		for (int i = 0; i < itemNum; i++) {
			items.add(generateItem());
		}

		Items ret = new Items();
		ret.setItems(items);
		return ret;
	}

	public static Item generateItem() {
		Item item = (Item) anyItemFromList(testItems);
		return item;
	}

}
