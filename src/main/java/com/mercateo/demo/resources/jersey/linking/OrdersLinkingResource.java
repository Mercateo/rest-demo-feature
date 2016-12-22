package com.mercateo.demo.resources.jersey.linking;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.mercateo.common.rest.schemagen.JerseyResource;
import com.mercateo.demo.feature.Feature;
import com.mercateo.demo.feature.KnownFeatureId;
import com.mercateo.demo.feature.TypedFeatureChecker;
import com.mercateo.demo.resources.Paths;
import com.mercateo.demo.resources.json.OrderJson;
import com.mercateo.demo.resources.json.SendBackJson;
import com.mercateo.demo.services.OrderService;

import lombok.Getter;

@Path(Paths.ORDERS_LINKING)
public class OrdersLinkingResource implements JerseyResource {

	@Inject
	private OrderService orderService;

	@Inject
	@Getter
	private TypedFeatureChecker featureChecker;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public OrdersWrapper getOrders(@QueryParam("offset") @DefaultValue("0") Integer offset,
			@QueryParam("limit") @DefaultValue("100") Integer limit) {

		List<OrderJson> orders = orderService.getOrders(offset, limit, null);
		List<OrderWrapper> list = orders.stream().map(this::create).collect(Collectors.toList());
		return OrdersWrapper.builder().members(list).//
				totalCount(orderService.getTotalCount()).//
				limit(limit).//
				offset(offset).build();
	}

	private OrderWrapper create(OrderJson order) {
		return OrderWrapper.builder().order(order).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{orderId}")
	public OrderWrapper getOrder(@PathParam("orderId") String id) {
		OrderJson order = orderService.getOrder(id);
		return create(order);

	}

	@Path("{orderId}/send-back")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Feature(KnownFeatureId.TICKET_5)
	public void sendBack(@PathParam("orderId") @NotNull String orderId, @NotNull SendBackJson sendBackJson) {
		orderService.sendBack(orderId, sendBackJson);
	}

}
