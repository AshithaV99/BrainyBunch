package com.loanservice.us4.Exception;

public class ExceededLoanLimitException extends RuntimeException{
    public ExceededLoanLimitException(String message){
        super(message);
    }
}
