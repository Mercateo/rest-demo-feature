/**
 * Copyright © 2018 Mercateo AG (http://www.mercateo.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mercateo.demo.resources.orders;

import java.util.Optional;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;

import com.mercateo.common.rest.schemagen.JerseyResource;
import com.mercateo.common.rest.schemagen.JsonHyperSchema;
import com.mercateo.common.rest.schemagen.link.LinkFactory;
import com.mercateo.common.rest.schemagen.link.LinkMetaFactory;
import com.mercateo.common.rest.schemagen.link.relation.Rel;
import com.mercateo.common.rest.schemagen.link.relation.RelType;
import com.mercateo.common.rest.schemagen.link.relation.Relation;
import com.mercateo.common.rest.schemagen.parameter.CallContext;
import com.mercateo.common.rest.schemagen.parameter.Parameter;
import com.mercateo.common.rest.schemagen.types.ObjectWithSchema;
import com.mercateo.common.rest.schemagen.types.PaginatedResponse;
import com.mercateo.common.rest.schemagen.types.PaginatedResponseBuilderCreator;
import com.mercateo.demo.feature.Feature;
import com.mercateo.demo.feature.KnownFeatureId;
import com.mercateo.demo.resources.Paths;
import com.mercateo.demo.resources.returns.CreateReturnJson;
import com.mercateo.demo.resources.returns.CreateSendBackJson;
import com.mercateo.demo.resources.returns.ReturnRel;
import com.mercateo.demo.resources.returns.ReturnsResource;
import com.mercateo.demo.services.order.Order;
import com.mercateo.demo.services.order.OrderId;
import com.mercateo.demo.services.order.OrderService;
import com.mercateo.demo.services.order.STATE;
import com.mercateo.demo.services.returns.ReturnId;
import com.mercateo.demo.services.returns.ReturnsReadRepo;
import com.mercateo.demo.services.returns.ReturnsWriteService;

import lombok.extern.slf4j.Slf4j;

@Path(Paths.ORDERS)
@Slf4j
public class OrdersResource implements JerseyResource {
	@Inject
	private LinkMetaFactory linkMetaFactory;

	@Inject
	private OrderService orderService;

	@Inject
	private ReturnsWriteService returnsWriteService;

	@Inject
	private ReturnsReadRepo returnsReadService;

	@Inject
	private PaginatedResponseBuilderCreator responseBuilderCreator;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public PaginatedResponse<OrderJson> getOrders(@BeanParam SearchQueryBean searchQueryBean) {
		Integer limit = searchQueryBean.getLimit();
		Integer offset = searchQueryBean.getOffset();
		OrderId id = searchQueryBean.getId();

		LinkFactory<OrdersResource> ordersLinkFactory = linkMetaFactory.createFactoryFor(OrdersResource.class);

		return responseBuilderCreator.<Order, OrderJson> builder().withList(orderService.getOrders(offset, limit, id))
				.withPaginationLinkCreator((rel, targetOffset, targetLimit) -> ordersLinkFactory.forCall(rel,
						r -> r.getOrders(new SearchQueryBean(targetOffset, targetLimit))))
				// get a search for a specific order
				.withContainerLinks(ordersLinkFactory.forCall(Relation.of("instance-search", RelType.OTHER),
						r -> r.getOrders(new SearchQueryBean())))
				.withElementMapper(this::createSchema).build();
	}

	private ObjectWithSchema<OrderJson> createSchema(Order order) {
		LinkFactory<OrdersResource> ordersLinkFactory = linkMetaFactory.createFactoryFor(OrdersResource.class);
		Optional<Link> self = ordersLinkFactory.forCall(Rel.SELF, r -> r.getOrder(order.getId()));
		LinkFactory<ReturnsResource> returnsLinkFactory = linkMetaFactory.createFactoryFor(ReturnsResource.class);
		Optional<Link> sendBack = Optional.empty();
		Optional<Link> sendBack2 = Optional.empty();
		Optional<Link> returnLink = Optional.empty();
		if (order.getState() == STATE.SHIPPED) {
			sendBack = ordersLinkFactory.forCall(OrderRel.SEND_BACK, r -> r.sendBack(order.getId(), null));
			final CallContext context = Parameter.createContext();
			final Parameter.Builder<CreateReturnJson> createReturnBuilder = context.builderFor(CreateReturnJson.class) //
					.allowValues(new CreateReturnJson(null, order.getId().getId()));
			sendBack2 = returnsLinkFactory.forCall(OrderRel.SEND_BACK_NOUN,
					r -> r.createNew(createReturnBuilder.build().get()), context);
		} else if (order.getState() == STATE.RETURNED) {

			returnLink = returnsReadService.findByOrderId(order.getId()).//
					map(ret -> returnsLinkFactory.forCall(OrderRel.RETURN, r -> r.getReturn(ret.getId())).orElse(null));
		}
		return ObjectWithSchema.create(OrderJson.from(order),
				JsonHyperSchema.from(self, sendBack, sendBack2, returnLink));
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{orderId}")
	public ObjectWithSchema<OrderJson> getOrder(@PathParam("orderId") OrderId id) {
		Order order = orderService.getOrder(id);
		return createSchema(order);

	}

	@Path("{orderId}/send-back")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Feature(KnownFeatureId.TICKET_5)
	@Produces(MediaType.APPLICATION_JSON)
	public ObjectWithSchema<Void> sendBack(@PathParam("orderId") @NotNull OrderId orderId,
			@NotNull CreateSendBackJson sendBackJson) {
		// includes check, if order could be sent back
		ReturnId returnId = returnsWriteService.create(sendBackJson, orderId);
		Optional<Link> orderLink = linkMetaFactory.createFactoryFor(OrdersResource.class).forCall(OrderRel.ORDER,
				r -> r.getOrder(orderId));

		Optional<Link> returnLink = linkMetaFactory.createFactoryFor(ReturnsResource.class).forCall(ReturnRel.RETURN,
				r -> r.getReturn(returnId));

		log.info("send back " + orderId + " with " + sendBackJson);
		return ObjectWithSchema.create(null, JsonHyperSchema.from(orderLink, returnLink));
	}
}
