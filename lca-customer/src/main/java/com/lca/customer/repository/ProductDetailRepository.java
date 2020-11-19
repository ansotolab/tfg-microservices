package com.lca.customer.repository;

import com.lca.customer.model.ProductDetail;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProductDetailRepository extends CrudRepository<ProductDetail, Long> {
    public Optional<ProductDetail> findById(long id);
}
