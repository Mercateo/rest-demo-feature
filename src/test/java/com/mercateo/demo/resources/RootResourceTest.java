package com.mercateo.demo.resources;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import javax.ws.rs.core.Link;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.mercateo.common.rest.schemagen.link.LinkMetaFactory;
import com.mercateo.demo.lib.schemagen.HyperSchemaCreator;

@RunWith(MockitoJUnitRunner.class)
public class RootResourceTest {
	@Mock
	private LinkMetaFactory linkMetaFactory;

	@Mock
	private HyperSchemaCreator hyperSchemaCreator;

	@InjectMocks
	private RootResource rootResource;

	@SuppressWarnings("unchecked")
	@Test
	public void callingRootResourceShouldReturnAvailableLinks() {
		when(linkMetaFactory.createFactoryFor(OrdersResource.class))
				.thenReturn(LinkMetaFactory.createInsecureFactoryForTest().createFactoryFor(OrdersResource.class));

		@SuppressWarnings("rawtypes")
		ArgumentCaptor<Optional> cap = ArgumentCaptor.forClass(Optional.class);
		rootResource.getRoot();
		verify(hyperSchemaCreator).create(any(), cap.capture());

		Optional<Link> value = cap.getValue();

		assertEquals(OrderRel.ORDERS.getRelation().getName(), value.get().getRel());

	}

}