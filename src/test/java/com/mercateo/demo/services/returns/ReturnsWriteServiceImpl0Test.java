package com.mercateo.demo.services.returns;

import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.Optional;

import javax.ws.rs.WebApplicationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.mercateo.demo.resources.returns.CreateSendBackJson;
import com.mercateo.demo.services.order.Order;
import com.mercateo.demo.services.order.OrderId;
import com.mercateo.demo.services.order.OrdersCrudRepo;
import com.mercateo.demo.services.order.STATE;

@RunWith(MockitoJUnitRunner.class)
public class ReturnsWriteServiceImpl0Test {
	@Mock
	private OrdersCrudRepo orderCrudRepo;

	@Mock
	private ReturnsCrudRepo returnsCrudRepo;

	@Mock
	private ReturnsReadRepo returnsReadRepo;

	@InjectMocks
	private ReturnsWriteServiceImpl uut;

	@Test(expected = WebApplicationException.class)
	public void testCreate_no_order() throws Exception {
		CreateSendBackJson createSendBackJson = new CreateSendBackJson("hallo");
		OrderId orderId = OrderId.fromString("1");
		uut.create(createSendBackJson, orderId);
	}

	@Test
	public void testCreate_new() throws Exception {
		CreateSendBackJson createSendBackJson = new CreateSendBackJson("hallo");
		OrderId orderId = OrderId.fromString("1");
		Order order = new Order(orderId, 1.0d, STATE.SHIPPED);
		when(orderCrudRepo.findOne(orderId)).thenReturn(order);
		uut.create(createSendBackJson, orderId);
		verify(returnsCrudRepo).save(any(Return.class));
	}

	@Test
	public void testCreate_wrong_status() throws Exception {
		CreateSendBackJson createSendBackJson = new CreateSendBackJson("hallo");
		OrderId orderId = OrderId.fromString("1");
		Order order = new Order(orderId, 1.0d, STATE.CANCELED);
		when(orderCrudRepo.findOne(orderId)).thenReturn(order);

		try {
			uut.create(createSendBackJson, orderId);
			fail();
		} catch (WebApplicationException e) {
			verifyNoMoreInteractions(returnsCrudRepo, returnsReadRepo);
			return;
		}
		fail();
	}

	@Test
	public void testCreate__already_exists() throws Exception {
		CreateSendBackJson createSendBackJson = new CreateSendBackJson("hallo");
		OrderId orderId = OrderId.fromString("1");
		Order order = new Order(orderId, 1.0d, STATE.RETURNED);
		Return value = new Return(new Date(), orderId);
		when(returnsReadRepo.findByOrderId(orderId)).thenReturn(Optional.of(value));
		when(orderCrudRepo.findOne(orderId)).thenReturn(order);
		uut.create(createSendBackJson, orderId);
		verify(returnsReadRepo).findByOrderId(orderId);
	}
}
