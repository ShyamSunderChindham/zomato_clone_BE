package com.shyam_chindham.zomato_clone.exceptions;

public class ServiceUnavailableException extends RuntimeException {
    public ServiceUnavailableException(String message) {
        super(message);
    }
}