package com.mercateo.demo.resources.orders;

import com.mercateo.demo.services.order.Order;
import com.mercateo.demo.services.order.STATE;

import lombok.Value;

@Value
public class OrderJson {
	private String id;

	private double total;

	private STATE state;

	public static OrderJson from(Order orderEntity) {
		return new OrderJson(orderEntity.getId().getId(), orderEntity.getTotal(), orderEntity.getState());
	}
}
