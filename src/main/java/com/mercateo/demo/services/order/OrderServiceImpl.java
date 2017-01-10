package com.mercateo.demo.services.order;

import java.util.ArrayList;
import java.util.Arrays;

import javax.validation.constraints.NotNull;
import javax.ws.rs.WebApplicationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.mercateo.common.rest.schemagen.types.PaginatedList;
import com.mercateo.demo.services.OffsetBasedPageRequest;

import lombok.NonNull;

@Service
public class OrderServiceImpl implements OrderService {

	private OrdersCrudRepo ordersCrudRepo;

	@Autowired
	public OrderServiceImpl(@NonNull OrdersCrudRepo ordersCrudRepo) {
		this.ordersCrudRepo = ordersCrudRepo;
	}

	@Override
	public PaginatedList<Order> getOrders(Integer offset, Integer limit, OrderId idOrNull) {
		if (idOrNull != null) {
			Order order = ordersCrudRepo.findOne(idOrNull);
			if (order != null) {
				return new PaginatedList<>(1, offset, limit, Arrays.asList(order));
			} else {
				return new PaginatedList<>(0, offset, limit, new ArrayList<>());
			}
		}
		Page<Order> page = ordersCrudRepo.findAll(new OffsetBasedPageRequest(offset, limit));
		return new PaginatedList<>(page.getNumberOfElements(), offset, limit, page.getContent());
	}

	@Override
	public Order getOrder(@NotNull OrderId orderId) {
		Order order = ordersCrudRepo.findOne(orderId);

		if (order == null) {
			throw new WebApplicationException(404);
		}
		return order;
	}

}
