package com.lca.stock.service;

import com.lca.stock.model.Material;
import com.lca.stock.model.Product;
import com.lca.stock.model.ProductMaterial;
import com.lca.stock.model.ProductPack;
import com.lca.stock.repository.ProductMaterialRepository;
import com.lca.stock.repository.ProductPackRepository;
import com.lca.stock.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductMaterialRepository productMaterialRepository;
    @Autowired
    private ProductPackRepository productPackRepository;

    public Product saveProduct(Product product) {
        // 0. Get materials and pack
        List<ProductMaterial> productMaterials = product.getMaterials();
        List<ProductPack> productPacks = product.getChildProducts();

        // 1. Save the product
        Product productSaved = productRepository.save(product);

        // 2. Save the material quantity with product
        if (productMaterials != null) {
            for (ProductMaterial p : productMaterials) {
                p.setProduct(productSaved);
                productMaterialRepository.save(p);
            }
        }

        // 3. If it's a pack, save pack
        if (productPacks != null) {
            for (ProductPack p : productPacks) {
                p.setParent(productSaved);
                productPackRepository.save(p);
            }
        }

        return productRepository.findById(productSaved.getId()).get();
    }

    public List<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        productRepository.findAll().iterator().forEachRemaining(products::add);
        return products;
    }
}
