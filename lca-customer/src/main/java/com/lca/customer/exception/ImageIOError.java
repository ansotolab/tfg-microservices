package com.lca.customer.exception;

public class ImageIOError extends RuntimeException {
    public ImageIOError() {
        super("There was an unexpected error reading the file.");
    }
}