/**
 * Copyright © 2018 Mercateo AG (http://www.mercateo.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mercateo.demo.services.returns;

import java.util.Date;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;

import org.springframework.stereotype.Service;

import com.mercateo.demo.resources.returns.CreateSendBackJson;
import com.mercateo.demo.services.order.Order;
import com.mercateo.demo.services.order.OrderId;
import com.mercateo.demo.services.order.OrdersCrudRepo;
import com.mercateo.demo.services.order.STATE;

import lombok.NonNull;

@Service
public class ReturnsWriteServiceImpl implements ReturnsWriteService {

	private ReturnsCrudRepo returnsCrudRepo;

	private OrdersCrudRepo orderCrudRepo;

	private ReturnsReadRepo returnsReadRepo;

	@Inject
	public ReturnsWriteServiceImpl(@NonNull ReturnsCrudRepo returnsCrudRepo, @NonNull OrdersCrudRepo orderCrudRepo,
			@NonNull ReturnsReadRepo returnsReadRepo) {
		this.returnsReadRepo = returnsReadRepo;
		this.returnsCrudRepo = returnsCrudRepo;
		this.orderCrudRepo = orderCrudRepo;
	}

	@Override
	public ReturnId create(@NonNull CreateSendBackJson createSendBackJson, @NonNull OrderId orderId) {
		Order order = orderCrudRepo.findOne(orderId);
		if (order == null) {
			throw new WebApplicationException(400);
		}
		STATE state = order.getState();
		switch (state) {
		case RETURNED:
			return returnsReadRepo.findByOrderId(orderId).get().getId();
		case SHIPPED:
			return createNew(createSendBackJson, order);
		default:
			throw new WebApplicationException(400);
		}
	}

	private ReturnId createNew(CreateSendBackJson createSendBackJson, Order order) {
		order.setState(STATE.RETURNED);
		orderCrudRepo.save(order);
		Return returnEntity = new Return(new Date(), order.getId());
		returnEntity.setCarrier(createSendBackJson.getPreferredCarrier());
		returnsCrudRepo.save(returnEntity);
		return ReturnId.fromString(returnEntity.getId().toString());
	}

}
