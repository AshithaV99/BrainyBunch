package com.loanservice.us4.Exception;

public class BookNotAvailableException extends RuntimeException{
    public BookNotAvailableException(String message){
        super(message);
    }

}
