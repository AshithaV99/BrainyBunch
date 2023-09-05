package com.loanservice.us4.Exception;

public class LoanNotFoundException extends RuntimeException{
    public LoanNotFoundException(String message){
        super(message);
    }
}
