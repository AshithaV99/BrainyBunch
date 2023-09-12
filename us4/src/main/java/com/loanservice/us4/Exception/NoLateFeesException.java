package com.loanservice.us4.Exception;

public class NoLateFeesException extends RuntimeException{
    public NoLateFeesException(String message){
        super(message);
    }
}
