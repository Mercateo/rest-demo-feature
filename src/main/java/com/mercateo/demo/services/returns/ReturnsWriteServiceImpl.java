package com.mercateo.demo.services.returns;

import java.util.Date;

import javax.ws.rs.WebApplicationException;

import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
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
