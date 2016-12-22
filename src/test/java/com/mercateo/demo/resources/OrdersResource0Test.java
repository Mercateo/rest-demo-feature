package com.mercateo.demo.resources;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.mercateo.common.rest.schemagen.link.LinkMetaFactory;
import com.mercateo.common.rest.schemagen.link.relation.Rel;
import com.mercateo.common.rest.schemagen.types.ObjectWithSchema;
import com.mercateo.common.rest.schemagen.types.PaginatedResponse;
import com.mercateo.common.rest.schemagen.types.PaginatedResponseBuilderCreator;
import com.mercateo.demo.resources.json.OrderJson;
import com.mercateo.demo.resources.json.SendBackJson;
import com.mercateo.demo.services.OrderService;
import com.mercateo.demo.services.STATE;

@RunWith(MockitoJUnitRunner.class)
public class OrdersResource0Test {

	@Spy
	private LinkMetaFactory linkMetaFactory = LinkMetaFactory.createInsecureFactoryForTest();

	@Spy
	private PaginatedResponseBuilderCreator responseBuilderCreator = new PaginatedResponseBuilderCreator();
	@Mock
	private OrderService orderService;

	@InjectMocks
	private OrdersResource uut;

	@Test
	public void testGetOrders() throws Exception {
		OrderJson orderJson = new OrderJson("1", 2, STATE.SHIPPED);
		when(orderService.getOrders(0, 20, null)).thenReturn(Arrays.asList(orderJson));
		when(orderService.getTotalCount()).thenReturn(1);

		PaginatedResponse<OrderJson> resp = uut.getOrders(new SearchQueryBean(0, 20));

		verify(orderService).getOrders(0, 20, null);
		verify(orderService).getTotalCount();
		verifyNoMoreInteractions(orderService);

		ObjectWithSchema<OrderJson> order = resp.object.members.get(0);
		assertTrue(order.schema.getByRel(Rel.SELF).isPresent());
		assertTrue(order.schema.getByRel(OrderRel.SEND_BACK).isPresent());
	}

	@Test
	public void testGetOrder() throws Exception {

		OrderJson orderJson = new OrderJson("1", 2, STATE.SHIPPED);
		when(orderService.getOrder("1")).thenReturn(orderJson);

		ObjectWithSchema<OrderJson> order = uut.getOrder("1");

		verify(orderService).getOrder("1");
		verifyNoMoreInteractions(orderService);

		assertTrue(order.schema.getByRel(Rel.SELF).isPresent());
		assertTrue(order.schema.getByRel(OrderRel.SEND_BACK).isPresent());
	}

	@Test
	public void testSendBack() throws Exception {
		SendBackJson sendBackJson = new SendBackJson("hallo");
		uut.sendBack("2", sendBackJson);

		verify(orderService).sendBack("2", sendBackJson);

	}

}
