package com.sda.store.excpetion;

public class ResourceNotFoundInDatabase extends RuntimeException{

    public ResourceNotFoundInDatabase (String message) {
        super(message);
    }
}
