package com.lca.stock.exception;

public class MaterialNotFound extends RuntimeException {
    public MaterialNotFound(long id) {
        super("The material with id: " + id + " does not exist.");
    }
}
