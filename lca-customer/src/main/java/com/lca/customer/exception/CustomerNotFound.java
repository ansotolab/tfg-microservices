package com.lca.customer.exception;

public class CustomerNotFound extends RuntimeException {
    public CustomerNotFound(long id) {
        super("The customer with id: " + id + " does not exist.");
    }
}
