package com.loanservice.us4.Controller;

import com.loanservice.us4.Dto.LoanDTO;
import com.loanservice.us4.Entity.LoanRecord;
import com.loanservice.us4.Service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
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
}
   /* @PostMapping("/return")
    public ResponseEntity<BigDecimal> returnBook(@PathVariable Long loanId){
        return ResponseEntity.ok(loanService.returnBook(loanId));
    }
    //The returnBook method returns the updated LoanRecord object with the calculated late fee

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<LoanRecord>> getUserLoans(@PathVariable Long userId) {
        List<LoanRecord> userLoans = loanService.getUserLoans(userId);
        return ResponseEntity.ok(userLoans);
    // retrieving the loan records of a specific user based on their user ID
    }
    @PostMapping("/clear-late-fees/{userId}")
    public ResponseEntity<Void> clearLateFees(@PathVariable Long userId) {
        loanService.clearLateFees(userId);
        return ResponseEntity.ok().build();
    // clear late fees based on id
    }

}*/
