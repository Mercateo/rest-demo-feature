package com.mercateo.demo.resources;

import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;

import com.mercateo.common.rest.schemagen.JerseyResource;
import com.mercateo.common.rest.schemagen.JsonHyperSchema;
import com.mercateo.common.rest.schemagen.link.LinkMetaFactory;
import com.mercateo.common.rest.schemagen.types.ObjectWithSchema;
import com.mercateo.demo.resources.jersey.linking.OrdersLinkingResource;
import com.mercateo.demo.resources.orders.OrderRel;
import com.mercateo.demo.resources.orders.OrdersResource;
import com.mercateo.demo.resources.orders.SearchQueryBean;
import com.mercateo.demo.resources.returns.ReturnRel;
import com.mercateo.demo.resources.returns.ReturnsResource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Path("/")
@Api(value = "/", description = "root resource")
public class RootResource implements JerseyResource {

	@Inject
	private LinkMetaFactory linkMetaFactory;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get all possible links")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success") })
	public ObjectWithSchema<PersonJson> getRoot() {
		Optional<Link> ordersLink = linkMetaFactory.createFactoryFor(OrdersResource.class).forCall(OrderRel.ORDERS,
				r -> r.getOrders(new SearchQueryBean(0, 20)));

		Optional<Link> ordersLinkingLink = linkMetaFactory.createFactoryFor(OrdersLinkingResource.class)
				.forCall(OrderRel.ORDERS_LINKING, r -> r.getOrders(0, 20));

		Optional<Link> returnsLink = linkMetaFactory.createFactoryFor(ReturnsResource.class).forCall(ReturnRel.RETURNS,
				r -> r.getReturns(new com.mercateo.demo.resources.returns.SearchQueryBean(0, 20)));
		return ObjectWithSchema.create(new PersonJson("Test", "Tester"),
				JsonHyperSchema.from(ordersLink, ordersLinkingLink, returnsLink));

	}
}
