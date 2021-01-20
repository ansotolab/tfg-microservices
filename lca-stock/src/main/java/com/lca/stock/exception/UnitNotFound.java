package com.lca.stock.exception;

public class UnitNotFound extends RuntimeException {
    public UnitNotFound(long id) {
        super("The unit with id: " + id + " does not exist.");
    }
}
