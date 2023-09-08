package com.loanservice.us4.Service;

import com.loanservice.us4.Dto.LoanDTO;
import com.loanservice.us4.Entity.*;
import com.loanservice.us4.Exception.BookNotAvailableException;
import com.loanservice.us4.Exception.LoanNotFoundException;
import com.loanservice.us4.Exception.UserNotFoundException;
import com.loanservice.us4.Repository.BookRepository;
import com.loanservice.us4.Repository.LoanRepository;
import com.loanservice.us4.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class LoanService {


    private static final long YOUR_DAILY_LATE_FEE_RATE = 5;
    private static final BigDecimal LATE_FEE_PER_DAY = BigDecimal.valueOf(1);

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    public LoanRecord issueBook(LoanDTO loan) {
        Long userId = loan.getUserId();
        Long bookId = loan.getBookId();

        // Check if the book exists by its ID
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotAvailableException("Book not found"));

        // Check if the book is available for a loan
        if (book.getStatus() != BookStatus.AVAILABLE) {
          throw new BookNotAvailableException("Book is not available for loan");
        }

        // Check if the user exists
        UserAccount user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        // Create a new loan record
        LoanRecord loanRecord = new LoanRecord();
        loanRecord.setUserId(userId);
        loanRecord.setBookId(bookId);
        loanRecord.setIssueDate(LocalDate.now());
        loanRecord.setDueDate(LocalDate.now().plusDays(14));

        // Save the loan record and update the user's loans
        loanRecord = loanRepository.save(loanRecord);

        book.setStatus(BookStatus.LOANED);
        bookRepository.save(book);

        // Add the loan record to the user's loans and save changes
        userRepository.save(user);
        return loanRecord;
    }

    public LateFeeInfo returnBook(Long loanId) {

            LoanRecord loanRecord = loanRepository.findById(loanId)
                    .orElseThrow(() -> new LoanNotFoundException("Loan not found"));

            LocalDate dueDate = loanRecord.getDueDate();
            LocalDate today = LocalDate.now();
            BigDecimal lateFee = BigDecimal.ZERO;

            if (today.isAfter(dueDate)) {
                long daysLate = DAYS.between(dueDate, today);
                lateFee = BigDecimal.valueOf(daysLate).multiply(LATE_FEE_PER_DAY);

                loanRecord.setLateFee(lateFee);
                loanRepository.save(loanRecord);
                UserAccount user = userRepository.findById(loanRecord.getUserId())
                        .orElseThrow(() -> new UserNotFoundException("User not found"));

                user.setTotalLateFees(user.getTotalLateFees().add(lateFee));
                userRepository.save(user);
            }

            // Assuming ReturnResponse has a constructor that accepts lateFee as a parameter
            return new LateFeeInfo(lateFee);

            }




   /* public List<LoanRecord> getUserLoans(Long userId) {
        List<LoanRecord> userLoans = loanRepository.findByUserId(userId);
        return userLoans;
    //To get the list of loan records
    }*/

   /* public void clearLateFees(Long userId) {
        // Retrieve all loans for the specified user
        List<LoanRecord> userLoans = loanRepository.findByUserId(userId);

        BigDecimal totalLateFees = BigDecimal.ZERO;

        // Calculate the total late fees for the user
        for (LoanRecord loan : LateFeeInfo) {
            BigDecimal lateFee = loan.getLateFee();
            if (lateFee != null) {
                totalLateFees = totalLateFees.add(lateFee);
            }
        }

        // Clear the late fees for the user
        if (totalLateFees.compareTo(BigDecimal.ZERO) > 0) {
            UserAccount user = userRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException("User not found"));

            user.setTotalLateFees(BigDecimal.ZERO); // Assume have a field for total late fees in UserAccount
            userRepository.save(user);
        }
    }*/

    public void clearFee(Long loanId) {
        LoanRecord loanRecord = loanRepository.findById(loanId)
                .orElseThrow(() -> new LoanNotFoundException("Loan not found"));

        if (loanRecord.getLateFee().compareTo(BigDecimal.ZERO) > 0) {
            loanRecord.setLateFee(BigDecimal.ZERO);
            loanRepository.save(loanRecord);

            UserAccount user = userRepository.findById(loanRecord.getUserId())
                    .orElseThrow(() -> new UserNotFoundException("User not found"));

            // Subtract the late fee from the user's total late fees
            user.setTotalLateFees(user.getTotalLateFees().subtract(loanRecord.getLateFee()));
            userRepository.save(user);
        }
    }
}
