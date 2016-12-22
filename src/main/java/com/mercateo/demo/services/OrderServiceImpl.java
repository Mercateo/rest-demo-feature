package com.mercateo.demo.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.ws.rs.WebApplicationException;

import com.mercateo.demo.resources.json.OrderJson;
import com.mercateo.demo.resources.json.SendBackJson;

public class OrderServiceImpl implements OrderService {

	private static final OrderJson one = new OrderJson("1", 334.40, STATE.OPEN);
	private static final OrderJson two = new OrderJson("2", 200, STATE.SHIPPED);

	@Override
	public List<OrderJson> getOrders(Integer offset, Integer limit, String idOrNull) {
		if (idOrNull != null) {
			if (idOrNull.equals("1")) {
				return Arrays.asList(one);
			}
			if (idOrNull.equals("2")) {
				return Arrays.asList(two);
			} else {
				return new ArrayList<>();
			}
		}
		return Arrays.asList(one, two);
	}

	@Override
	public OrderJson getOrder(@NotNull String id) {
		switch (id) {
		case "1":
			return one;
		case "2":
			return two;

		default:
			throw new WebApplicationException(404);
		}
	}

	@Override
	public void sendBack(String orderId, SendBackJson sendBackJson) {
		// nothing

	}

	@Override
	public int getTotalCount() {
		return 2;
	}

}
