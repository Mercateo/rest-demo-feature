package com.mercateo.demo.resources.returns;

import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;

import org.springframework.data.domain.Page;

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
import com.mercateo.demo.resources.Paths;
import com.mercateo.demo.resources.orders.OrderRel;
import com.mercateo.demo.resources.orders.OrdersResource;
import com.mercateo.demo.services.OffsetBasedPageRequest;
import com.mercateo.demo.services.order.OrderId;
import com.mercateo.demo.services.returns.Return;
import com.mercateo.demo.services.returns.ReturnId;
import com.mercateo.demo.services.returns.ReturnsReadRepo;
import com.mercateo.demo.services.returns.ReturnsWriteService;

import lombok.extern.slf4j.Slf4j;

@Path(Paths.RETURNS)
@Feature(KnownFeatureId.TICKET_5)
@Slf4j
public class ReturnsResource implements JerseyResource {
	@Inject
	private LinkMetaFactory linkMetaFactory;

	@Inject
	private ReturnsWriteService returnsWriteService;

	@Inject
	private ReturnsReadRepo returnsReadService;

	@Inject
	private PaginatedResponseBuilderCreator responseBuilderCreator;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public PaginatedResponse<ReturnJson> getReturns(@BeanParam SearchQueryBean searchQueryBean) {
		Integer limit = searchQueryBean.getLimit();
		Integer offset = searchQueryBean.getOffset();

		LinkFactory<ReturnsResource> ordersLinkFactory = linkMetaFactory.createFactoryFor(ReturnsResource.class);

		Page<Return> returningList = returnsReadService
				.findAll(new OffsetBasedPageRequest(searchQueryBean.offset, searchQueryBean.limit));

		return responseBuilderCreator.<Return, ReturnJson> builder()
				.withList(new PaginatedList<Return>((int) returningList.getTotalElements(), offset, limit,
						returningList.getContent()))
				.withPaginationLinkCreator((rel, targetOffset, targetLimit) -> ordersLinkFactory.forCall(rel,
						r -> r.getReturns(new SearchQueryBean(targetOffset, targetLimit))))
				.withElementMapper(this::createSchema)//
				.withContainerLinks(linkMetaFactory.createFactoryFor(ReturnsResource.class).forCall(Rel.CREATE,
						r -> r.createNew(null)))//
				.build();
	}

	private ObjectWithSchema<ReturnJson> createSchema(Return currentReturn) {
		LinkFactory<ReturnsResource> returnsLinkFactory = linkMetaFactory.createFactoryFor(ReturnsResource.class);
		Optional<Link> self = returnsLinkFactory.forCall(Rel.SELF, r -> r.getReturn(currentReturn.getId()));
		LinkFactory<OrdersResource> ordersLinkFactory = linkMetaFactory.createFactoryFor(OrdersResource.class);
		Optional<Link> orderLink = ordersLinkFactory.forCall(OrderRel.ORDER,
				r -> r.getOrder(currentReturn.getOrderId()));

		return ObjectWithSchema.create(ReturnJson.from(currentReturn), JsonHyperSchema.from(self, orderLink));
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{returnId}")
	public ObjectWithSchema<ReturnJson> getReturn(@PathParam("returnId") ReturnId id) {
		Return returnEntity = returnsReadService.findById(id);
		if (returnEntity == null) {
			throw new WebApplicationException(404);
		}
		return createSchema(returnEntity);

	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public ObjectWithSchema<Void> createNew(CreateReturnJson createJson) {
		OrderId orderId = OrderId.fromString(createJson.getOrderId());
		ReturnId returnId = returnsWriteService.create(createJson, orderId);
		Optional<Link> orderLink = linkMetaFactory.createFactoryFor(OrdersResource.class).forCall(OrderRel.ORDER,
				r -> r.getOrder(orderId));

		Optional<Link> returnLink = linkMetaFactory.createFactoryFor(ReturnsResource.class).forCall(ReturnRel.RETURN,
				r -> r.getReturn(returnId));

		log.info("send back " + orderId + " with " + createJson);
		return ObjectWithSchema.create(null, JsonHyperSchema.from(orderLink, returnLink));
	}
}
