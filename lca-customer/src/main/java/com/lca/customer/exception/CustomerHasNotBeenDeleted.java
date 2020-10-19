package com.lca.customer.exception;

public class CustomerHasNotBeenDeleted extends RuntimeException {
    public CustomerHasNotBeenDeleted(long id) {
        super("The customer with id: " + id + " has not been deleted.");
    }
}
