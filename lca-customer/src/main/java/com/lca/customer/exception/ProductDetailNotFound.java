package com.lca.customer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductDetailNotFound extends RuntimeException {
    public ProductDetailNotFound() {
        super();
    }

    public ProductDetailNotFound(String message) {
        super(message);
    }

    public ProductDetailNotFound(String message, Throwable cause) {
        super(message, cause);
    }
}
