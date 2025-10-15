package com.shyam_chindham.zomato_clone.exceptions;

public class AccessDeniedException extends RuntimeException {
    public AccessDeniedException(String message){
        super(message);
    }
}
