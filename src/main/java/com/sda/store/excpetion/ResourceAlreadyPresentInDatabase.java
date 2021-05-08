package com.sda.store.excpetion;

public class ResourceAlreadyPresentInDatabase  extends RuntimeException{

    public ResourceAlreadyPresentInDatabase(String message){
        super(message);
    }
}
