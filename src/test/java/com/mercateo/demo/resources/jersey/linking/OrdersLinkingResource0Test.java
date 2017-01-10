package com.mercateo.demo.resources.jersey.linking;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.mercateo.common.rest.schemagen.types.PaginatedList;
import com.mercateo.demo.feature.TypedFeatureChecker;
import com.mercateo.demo.resources.returns.CreateSendBackJson;
import com.mercateo.demo.services.order.Order;
import com.mercateo.demo.services.order.OrderId;
import com.mercateo.demo.services.order.OrderService;
import com.mercateo.demo.services.order.STATE;
import com.mercateo.demo.services.returns.ReturnsWriteService;

@RunWith(MockitoJUnitRunner.class)
public class OrdersLinkingResource0Test {
	@Mock
	private TypedFeatureChecker featureChecker;

	@Mock
	private OrderService orderService;
	@Mock
	private ReturnsWriteService returnsWriteService;

	@InjectMocks
	private OrdersLinkingResource uut;

	@Test
	public void testGetOrders() throws Exception {
		Order orderJson = new Order(OrderId.fromString("1"), 2, STATE.SHIPPED);
		when(orderService.getOrders(0, 20, null)).thenReturn(new PaginatedList<>(1, 0, 20, Arrays.asList(orderJson)));

		uut.getOrders(0, 20);

		verify(orderService).getOrders(0, 20, null);
		verifyNoMoreInteractions(orderService);

		verify(featureChecker, times(0)).isTicket_5();
		// checking of correct links not possible here
	}

	@Test
	public void testGetOrder() throws Exception {

		Order orderJson = new Order(OrderId.fromString("1"), 2, STATE.SHIPPED);
		when(orderService.getOrder(OrderId.fromString("1"))).thenReturn(orderJson);

		uut.getOrder(OrderId.fromString("1"));

		verify(orderService).getOrder(OrderId.fromString("1"));
		verifyNoMoreInteractions(orderService);
		verify(featureChecker, times(0)).isTicket_5();
	}

	@Test
	public void testSendBack() throws Exception {
		CreateSendBackJson sendBackJson = new CreateSendBackJson("hallo");
		uut.sendBack(OrderId.fromString("2"), sendBackJson);

		verify(returnsWriteService).create(sendBackJson, OrderId.fromString("2"));

	}

}
