package com.lca.customer.exception;

public class ImageInvalidType extends RuntimeException {
    public ImageInvalidType() {
        super("The valid type files are: jpg, png, gif, tiff.");
    }
}