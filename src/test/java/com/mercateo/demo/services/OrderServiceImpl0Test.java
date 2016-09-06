package com.mercateo.demo.services;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.ws.rs.WebApplicationException;

import org.junit.Test;

import com.mercateo.demo.resources.json.OrderJson;

public class OrderServiceImpl0Test {

	private OrderServiceImpl uut = new OrderServiceImpl();

	@Test
	public void testGetOrders() throws Exception {
		List<OrderJson> list = uut.getOrders(0, 20);
		assertEquals(2, list.size());
	}

	@Test
	public void testGetOrder() throws Exception {
		uut.getOrder("1");
		uut.getOrder("2");
	}

	@Test(expected = WebApplicationException.class)
	public void testGetOrderNotFound() throws Exception {
		uut.getOrder("3");
	}

	@Test
	public void testGetTotalCount() throws Exception {
		assertEquals(2, uut.getTotalCount());
	}

}
