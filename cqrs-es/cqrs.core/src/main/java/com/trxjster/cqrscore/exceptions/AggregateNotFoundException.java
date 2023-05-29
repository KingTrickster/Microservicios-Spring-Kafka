package com.trxjster.cqrscore.exceptions;

public class AggregateNotFoundException extends RuntimeException{
    public AggregateNotFoundException(String message){
        super(message);
    }
}
