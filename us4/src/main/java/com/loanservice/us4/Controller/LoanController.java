package com.loanservice.us4.Controller;

import com.loanservice.us4.Dto.LoanDTO;
import com.loanservice.us4.Dto.LoanReturnDTO;
import com.loanservice.us4.Entity.LateFeeInfo;
import com.loanservice.us4.Entity.LoanRecord;
import com.loanservice.us4.Exception.BookNotAvailableException;
import com.loanservice.us4.Exception.NoLateFeesException;
import com.loanservice.us4.Exception.UserNotFoundException;
import com.loanservice.us4.Service.LoanService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/loans")
public class LoanController {
    @Autowired
    private LoanService loanService;


    @PostMapping("/issue")
    public ResponseEntity<LoanRecord> issueBook(@RequestBody LoanDTO loan) {
        LoanRecord issuedLoan = loanService.issueBook(loan);
        return ResponseEntity.ok(issuedLoan);
    }

    @PostMapping("/return")
    public ResponseEntity<?> returnBookLoanedBook(@RequestBody LoanReturnDTO loanReturn) {
        try {
            LoanRecord returnedLoan = loanService.returnBook(loanReturn);
            return ResponseEntity.ok(returnedLoan);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/returnLateFee/{loanId}")
    public ResponseEntity<?> returnBook(@PathVariable Long loanId) {
        try {return  loanService.returnBook(loanId);}
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/clear-late-fees/{loanId}")
    public ResponseEntity<String> clearLateFees(@PathVariable Long loanId) {
        try {
            loanService.clearLateFee(loanId);
            return ResponseEntity.ok().build();
        } catch (NoLateFeesException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No late fees to clear for this loan.");
        }
    }

    @PostMapping("/updateLateFee/{loanId}")
    public ResponseEntity<?> updateLateFee(@PathVariable Long loanId, @RequestBody BigDecimal lateFee) {
        try {
            LoanRecord updatedLoanRecord = loanService.updateLateFee(loanId, lateFee);
            return new ResponseEntity<>(updatedLoanRecord, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}

       // clear late fees based on id













