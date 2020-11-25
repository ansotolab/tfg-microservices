package com.lca.stock.controller;

import com.lca.stock.model.Product;
import com.lca.stock.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("")
    public List<Product> getAll() {
        return productService.getProducts();
    }

    @PostMapping("")
    public Product saveProduct(@Valid @RequestBody Product product) {
        return productService.saveProduct(product);
    }
}
