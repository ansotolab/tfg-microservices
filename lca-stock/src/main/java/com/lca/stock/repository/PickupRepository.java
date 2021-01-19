package com.lca.stock.repository;

import com.lca.stock.model.Pickup;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PickupRepository extends CrudRepository<Pickup, Long> {
}
