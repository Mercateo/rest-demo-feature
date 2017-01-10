package com.mercateo.demo.resources.orders;

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
import com.mercateo.common.rest.schemagen.types.PaginatedList;
import com.mercateo.common.rest.schemagen.types.PaginatedResponse;
import com.mercateo.common.rest.schemagen.types.PaginatedResponseBuilderCreator;
import com.mercateo.demo.resources.returns.CreateSendBackJson;
import com.mercateo.demo.services.order.Order;
import com.mercateo.demo.services.order.OrderId;
import com.mercateo.demo.services.order.OrderService;
import com.mercateo.demo.services.order.STATE;
import com.mercateo.demo.services.returns.ReturnId;
import com.mercateo.demo.services.returns.ReturnsWriteService;

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

	@Mock
	private ReturnsWriteService returnsWriteService;

	@Test
	public void testGetOrders() throws Exception {
		Order orderJson = new Order(OrderId.fromString("1"), 2, STATE.SHIPPED);
		when(orderService.getOrders(0, 20, null)).thenReturn(new PaginatedList<>(1, 0, 20, Arrays.asList(orderJson)));

		PaginatedResponse<OrderJson> resp = uut.getOrders(new SearchQueryBean(0, 20));

		verify(orderService).getOrders(0, 20, null);

		verifyNoMoreInteractions(orderService);

		ObjectWithSchema<OrderJson> order = resp.object.members.get(0);
		assertTrue(order.schema.getByRel(Rel.SELF).isPresent());
		assertTrue(order.schema.getByRel(OrderRel.SEND_BACK).isPresent());
	}

	@Test
	public void testGetOrder() throws Exception {

		Order orderJson = new Order(OrderId.fromString("1"), 2, STATE.SHIPPED);
		when(orderService.getOrder(OrderId.fromString("1"))).thenReturn(orderJson);

		ObjectWithSchema<OrderJson> order = uut.getOrder(OrderId.fromString("1"));

		verify(orderService).getOrder(OrderId.fromString("1"));
		verifyNoMoreInteractions(orderService);

		assertTrue(order.schema.getByRel(Rel.SELF).isPresent());
		assertTrue(order.schema.getByRel(OrderRel.SEND_BACK).isPresent());
	}

	@Test
	public void testSendBack() throws Exception {
		CreateSendBackJson sendBackJson = new CreateSendBackJson("hallo");
		when(returnsWriteService.create(sendBackJson, OrderId.fromString("2"))).thenReturn(ReturnId.fromString("1"));
		uut.sendBack(OrderId.fromString("2"), sendBackJson);

		verify(returnsWriteService).create(sendBackJson, OrderId.fromString("2"));

	}

}
