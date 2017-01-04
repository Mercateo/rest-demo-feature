package com.mercateo.demo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.mercateo.demo.services.order.Order;
import com.mercateo.demo.services.order.OrderId;
import com.mercateo.demo.services.order.OrdersCrudRepo;
import com.mercateo.demo.services.order.STATE;

import lombok.NonNull;

@Component
public class SetupRunner implements CommandLineRunner {
	private static final Order one = new Order(OrderId.fromString("1"), 334.40, STATE.OPEN);
	private static final Order two = new Order(OrderId.fromString("2"), 200, STATE.SHIPPED);

	private OrdersCrudRepo ordersCrudRepo;

	@Autowired
	public SetupRunner(@NonNull OrdersCrudRepo ordersCrudRepo) {
		this.ordersCrudRepo = ordersCrudRepo;
	}

	@Override
	public void run(String... arg0) throws Exception {
		ordersCrudRepo.save(one);
		ordersCrudRepo.save(two);

	}

}
