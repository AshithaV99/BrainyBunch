package com.loanservice.us4.Controller;

import com.loanservice.us4.Dto.LoanDTO;
import com.loanservice.us4.Entity.LateFeeInfo;
import com.loanservice.us4.Entity.LoanRecord;
import com.loanservice.us4.Entity.UserAccount;
import com.loanservice.us4.Service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/return/{loanId}")
    public ResponseEntity<LateFeeInfo> returnBook(@PathVariable Long loanId) {
        LateFeeInfo response = loanService.returnBook(loanId);
        return ResponseEntity.ok(response);
    }

    //The returnBook method returns the updated LoanRecord object with the calculated late fee


  /*  @PostMapping("/clear-late-fees/{userId}")
    public ResponseEntity<Void> clearLateFees(@PathVariable Long userId) {
        loanService.clearLateFees(userId);
        return ResponseEntity.ok().build();
    // clear late fees based on id
    }*/

    @PostMapping("/clearLateFee/{loanId}") // Define the endpoint URL
    public ResponseEntity<String> clearLateFee(@PathVariable Long loanId) {

            // Call the clearFee method to reset the late fee
            loanService.clearFee(loanId);
            return ResponseEntity.ok("Late fee cleared successfully.");

        }
        }

