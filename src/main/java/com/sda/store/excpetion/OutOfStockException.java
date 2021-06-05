package com.sda.store.excpetion;

public class OutOfStockException extends RuntimeException{

    public OutOfStockException(String message){
    super(message);
}
}
