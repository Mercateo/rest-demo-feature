package com.mercateo.demo.services.returns;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReturnsCrudRepo extends CrudRepository<Return, ReturnId> {

}
