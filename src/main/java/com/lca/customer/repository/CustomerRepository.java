package com.lca.customer.repository;

import com.lca.customer.model.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
    public Optional<Customer> findById(long id);
    public Optional<Customer> findByCif(String cif);
    public Optional<Customer> findByCifAndByIdNot(String cif, long id);
}
