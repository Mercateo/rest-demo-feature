package com.mercateo.demo.resources;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.mercateo.common.rest.schemagen.link.LinkMetaFactory;
import com.mercateo.common.rest.schemagen.types.ObjectWithSchema;
import com.mercateo.demo.resources.orders.OrderRel;
import com.mercateo.demo.resources.returns.ReturnRel;

@RunWith(MockitoJUnitRunner.class)
public class RootResourceTest {
	@Spy
	private LinkMetaFactory linkMetaFactory = LinkMetaFactory.createInsecureFactoryForTest();

	@InjectMocks
	private RootResource rootResource;

	@Test
	public void callingRootResourceShouldReturnAvailableLinks() {
		ObjectWithSchema<PersonJson> result = rootResource.getRoot();

		assertEquals(3, result.schema.getLinks().size());
		assertEquals(OrderRel.ORDERS.getRelation().getName(), result.schema.getLinks().get(0).getRel());
		assertEquals(OrderRel.ORDERS_LINKING.getRelation().getName(), result.schema.getLinks().get(1).getRel());
		assertEquals(ReturnRel.RETURNS.getRelation().getName(), result.schema.getLinks().get(2).getRel());

	}

}