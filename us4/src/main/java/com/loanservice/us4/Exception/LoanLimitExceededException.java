package com.loanservice.us4.Exception;

public class LoanLimitExceededException extends RuntimeException{
    public LoanLimitExceededException(String message){
        super(message);
    }
}
