package com.loanservice.us4.Exception;

import com.loanservice.us4.Entity.LoanRecord;

public class LoanNotFoundException extends RuntimeException{
    public LoanNotFoundException (String message){
        super(message);
    }
}
