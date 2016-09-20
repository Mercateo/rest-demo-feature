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

import com.mercateo.demo.feature.TypedFeatureChecker;
import com.mercateo.demo.resources.json.OrderJson;
import com.mercateo.demo.resources.json.SendBackJson;
import com.mercateo.demo.services.OrderService;
import com.mercateo.demo.services.STATE;

@RunWith(MockitoJUnitRunner.class)
public class OrdersLinkingResource0Test {
	@Mock
	private TypedFeatureChecker featureChecker;

	@Mock
	private OrderService orderService;

	@InjectMocks
	private OrdersLinkingResource uut;

	@Test
	public void testGetOrders() throws Exception {
		OrderJson orderJson = new OrderJson("1", 2, STATE.SHIPPED);
		when(orderService.getOrders(0, 20)).thenReturn(Arrays.asList(orderJson));
		when(orderService.getTotalCount()).thenReturn(1);

		uut.getOrders(0, 20);

		verify(orderService).getOrders(0, 20);
		verify(orderService).getTotalCount();
		verifyNoMoreInteractions(orderService);

		verify(featureChecker, times(0)).isTicket_5();
		// checking of correct links not possible here
	}

	@Test
	public void testGetOrder() throws Exception {

		OrderJson orderJson = new OrderJson("1", 2, STATE.SHIPPED);
		when(orderService.getOrder("1")).thenReturn(orderJson);

		uut.getOrder("1");

		verify(orderService).getOrder("1");
		verifyNoMoreInteractions(orderService);
		verify(featureChecker, times(0)).isTicket_5();
	}

	@Test
	public void testSendBack() throws Exception {
		SendBackJson sendBackJson = new SendBackJson("hallo");
		uut.sendBack("2", sendBackJson);

		verify(orderService).sendBack("2", sendBackJson);

	}

}
