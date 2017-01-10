package com.mercateo.demo.services.order;

import com.mercateo.common.rest.schemagen.types.PaginatedList;

public interface OrderService {
	public PaginatedList<Order> getOrders(Integer offset, Integer limit, OrderId idOrNull);

	public Order getOrder(OrderId id);

}
