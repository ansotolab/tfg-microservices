package com.lca.customer.exception;

public class ImageNotFound extends RuntimeException {
    public ImageNotFound(long id) {
        super("The image with id: " + id + " does not exist.");
    }
}
