package com.lca.customer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductDetailNotFound extends RuntimeException {
    public ProductDetailNotFound(long id) {
        super("The product detail with id: " + id + " does not exist.");
    }
}
