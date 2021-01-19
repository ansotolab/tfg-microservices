package com.lca.stock.repository;

import com.lca.stock.model.Input;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InputRepository extends CrudRepository<Input, Long> {
}
