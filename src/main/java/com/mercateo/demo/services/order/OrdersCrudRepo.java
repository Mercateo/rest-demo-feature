package com.mercateo.demo.services.order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersCrudRepo extends CrudRepository<Order, OrderId> {

	Page<Order> findAll(Pageable pageable);

}
