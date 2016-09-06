package com.mercateo.demo.resources;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.mercateo.common.rest.schemagen.JerseyResource;
import com.mercateo.common.rest.schemagen.link.LinkMetaFactory;
import com.mercateo.common.rest.schemagen.types.ObjectWithSchema;
import com.mercateo.demo.lib.schemagen.HyperSchemaCreator;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Path("/")
@Api(value = "/", description = "root resource")
public class RootResource implements JerseyResource {

	@Inject
	private LinkMetaFactory linkMetaFactory;
	@Inject
	private HyperSchemaCreator hyperSchemaCreator;

	@SuppressWarnings("unchecked")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get all possible links")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success") })
	public ObjectWithSchema<PersonJson> getRoot() {
		return hyperSchemaCreator.create(new PersonJson("Test", "Tester"), linkMetaFactory
				.createFactoryFor(OrdersResource.class).forCall(OrderRel.ORDERS, r -> r.getOrders(0, 20)));
	}
}
