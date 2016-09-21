package com.mercateo.demo.resources;

import java.util.Optional;

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
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;

import com.mercateo.common.rest.schemagen.JerseyResource;
import com.mercateo.common.rest.schemagen.JsonHyperSchema;
import com.mercateo.common.rest.schemagen.link.LinkFactory;
import com.mercateo.common.rest.schemagen.link.LinkMetaFactory;
import com.mercateo.common.rest.schemagen.link.relation.Rel;
import com.mercateo.common.rest.schemagen.types.ObjectWithSchema;
import com.mercateo.common.rest.schemagen.types.PaginatedList;
import com.mercateo.common.rest.schemagen.types.PaginatedResponse;
import com.mercateo.common.rest.schemagen.types.PaginatedResponseBuilderCreator;
import com.mercateo.demo.feature.Feature;
import com.mercateo.demo.feature.KnownFeatureId;
import com.mercateo.demo.resources.json.OrderJson;
import com.mercateo.demo.resources.json.SendBackJson;
import com.mercateo.demo.services.OrderService;
import com.mercateo.demo.services.STATE;

import lombok.extern.slf4j.Slf4j;

@Path(Paths.ORDERS)
@Slf4j
public class OrdersResource implements JerseyResource {
	@Inject
	private LinkMetaFactory linkMetaFactory;

	@Inject
	private OrderService orderService;

	@Inject
	private PaginatedResponseBuilderCreator responseBuilderCreator;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public PaginatedResponse<OrderJson> getOrders(@QueryParam("offset") @DefaultValue("0") Integer offset,
			@QueryParam("limit") @DefaultValue("100") Integer limit) {

		LinkFactory<OrdersResource> ordersLinkFactory = linkMetaFactory.createFactoryFor(OrdersResource.class);

		return responseBuilderCreator.<OrderJson, OrderJson> builder()
				.withList(new PaginatedList<>(orderService.getTotalCount(), offset, limit,
						orderService.getOrders(offset, limit)))
				.withPaginationLinkCreator((rel, targetOffset, targetLimit) -> ordersLinkFactory.forCall(rel,
						r -> r.getOrders(targetOffset, targetLimit)))
				.withElementMapper(this::create).build();
	}

	private ObjectWithSchema<OrderJson> create(OrderJson order) {
		LinkFactory<OrdersResource> ordersLinkFactory = linkMetaFactory.createFactoryFor(OrdersResource.class);
		Optional<Link> self = ordersLinkFactory.forCall(Rel.SELF, r -> r.getOrder(order.getId()));
		Optional<Link> sendBack = Optional.empty();
		if (order.getState() == STATE.SHIPPED) {
			sendBack = ordersLinkFactory.forCall(OrderRel.SEND_BACK, r -> r.sendBack(order.getId(), null));
		}
		return ObjectWithSchema.create(order, JsonHyperSchema.from(self, sendBack));
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{orderId}")
	public ObjectWithSchema<OrderJson> getOrder(@PathParam("orderId") String id) {
		OrderJson order = orderService.getOrder(id);
		return create(order);

	}

	@Path("{orderId}/send-back")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Feature(KnownFeatureId.TICKET_5)
	public void sendBack(@PathParam("orderId") @NotNull String orderId, @NotNull SendBackJson sendBackJson) {
		orderService.sendBack(orderId, sendBackJson);
		log.info("send back " + orderId + " with " + sendBackJson);
	}
}
