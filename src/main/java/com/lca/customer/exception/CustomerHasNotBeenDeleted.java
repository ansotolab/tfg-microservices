package com.lca.customer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class CustomerHasNotBeenDeleted extends RuntimeException {
    public CustomerHasNotBeenDeleted() {
        super();
    }

    public CustomerHasNotBeenDeleted(String message) {
        super(message);
    }

    public CustomerHasNotBeenDeleted(String message, Throwable cause) {
        super(message, cause);
    }
}
