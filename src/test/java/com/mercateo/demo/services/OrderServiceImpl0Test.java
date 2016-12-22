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
		List<OrderJson> list = uut.getOrders(0, 20, null);
		assertEquals(2, list.size());
	}

	@Test
	public void testGetOrders_one() throws Exception {
		List<OrderJson> list = uut.getOrders(0, 20, "1");
		assertEquals(1, list.size());
	}

	@Test
	public void testGetOrders_two() throws Exception {
		List<OrderJson> list = uut.getOrders(0, 20, "2");
		assertEquals(1, list.size());
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
