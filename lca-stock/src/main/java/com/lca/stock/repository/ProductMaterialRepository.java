package com.lca.stock.repository;

import com.lca.stock.model.ProductMaterial;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductMaterialRepository extends CrudRepository<ProductMaterial, Long> {
}
