package com.mercateo.demo.services.returns;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.mercateo.demo.services.order.OrderId;

@Repository
public interface ReturnsReadRepo extends org.springframework.data.repository.Repository<Return, ReturnId> {

	public Page<Return> findAll(Pageable pageable);

	Return findById(ReturnId returnId);

	Optional<Return> findByOrderId(OrderId orderId);
}
