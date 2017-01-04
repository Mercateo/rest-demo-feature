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
import com.mercateo.common.rest.schemagen.types.PaginatedList;
import com.mercateo.demo.feature.Feature;
import com.mercateo.demo.feature.KnownFeatureId;
import com.mercateo.demo.feature.TypedFeatureChecker;
import com.mercateo.demo.resources.Paths;
import com.mercateo.demo.resources.orders.OrderJson;
import com.mercateo.demo.resources.returns.CreateSendBackJson;
import com.mercateo.demo.services.order.Order;
import com.mercateo.demo.services.order.OrderId;
import com.mercateo.demo.services.order.OrderService;
import com.mercateo.demo.services.returns.ReturnsWriteService;

import lombok.Getter;

@Path(Paths.ORDERS_LINKING)
public class OrdersLinkingResource implements JerseyResource {

	@Inject
	private OrderService orderService;

	@Inject
	@Getter
	private TypedFeatureChecker featureChecker;

	@Inject
	private ReturnsWriteService returnsWriteService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public OrdersWrapper getOrders(@QueryParam("offset") @DefaultValue("0") Integer offset,
			@QueryParam("limit") @DefaultValue("100") Integer limit) {

		PaginatedList<Order> orders = orderService.getOrders(offset, limit, null);
		List<OrderWrapper> list = orders.members.stream().map(this::create).collect(Collectors.toList());
		return OrdersWrapper.builder().members(list).//
				totalCount(orders.total).//
				limit(limit).//
				offset(offset).build();
	}

	private OrderWrapper create(Order order) {
		return OrderWrapper.builder().order(OrderJson.from(order)).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{orderId}")
	public OrderWrapper getOrder(@PathParam("orderId") OrderId id) {
		Order order = orderService.getOrder(id);
		return create(order);

	}

	@Path("{orderId}/send-back")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Feature(KnownFeatureId.TICKET_5)
	public void sendBack(@PathParam("orderId") @NotNull OrderId orderId, @NotNull CreateSendBackJson sendBackJson) {
		returnsWriteService.create(sendBackJson, orderId);
	}

}
