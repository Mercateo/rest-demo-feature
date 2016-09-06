package com.mercateo.demo.services;

import java.util.List;

import com.mercateo.demo.resources.json.OrderJson;
import com.mercateo.demo.resources.json.SendBackJson;

public interface OrderService {
	public List<OrderJson> getOrders(Integer offset, Integer limit);

	public OrderJson getOrder(String id);

	public void sendBack(String orderId, SendBackJson sendBackJson);

	public int getTotalCount();

}
