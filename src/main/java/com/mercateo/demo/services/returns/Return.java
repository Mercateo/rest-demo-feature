package com.mercateo.demo.services.returns;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.mercateo.demo.resources.CARRIER;
import com.mercateo.demo.services.order.OrderId;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Return {
	private CARRIER carrier;

	@NonNull
	private Date date;
	@NonNull
	private OrderId orderId;

	@Id
	private ReturnId id = ReturnId.fromString(UUID.randomUUID().toString());
}
