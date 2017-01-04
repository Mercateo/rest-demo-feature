package com.mercateo.demo.services.returns;

import com.mercateo.demo.resources.returns.CreateSendBackJson;
import com.mercateo.demo.services.order.OrderId;

public interface ReturnsWriteService {
	ReturnId create(CreateSendBackJson createSendBackJson, OrderId orderId);
}
