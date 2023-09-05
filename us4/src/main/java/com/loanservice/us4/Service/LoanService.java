package com.loanservice.us4.Service;

import com.loanservice.us4.Dto.LoanDTO;
import com.loanservice.us4.Entity.Book;
import com.loanservice.us4.Entity.BookStatus;
import com.loanservice.us4.Entity.LoanRecord;
import com.loanservice.us4.Entity.UserAccount;
import com.loanservice.us4.Exception.BookNotAvailableException;
import com.loanservice.us4.Exception.LoanLimitExceededException;
import com.loanservice.us4.Exception.LoanNotFoundException;
import com.loanservice.us4.Exception.UserNotFoundException;
import com.loanservice.us4.Repository.BookRepository;
import com.loanservice.us4.Repository.LoanRepository;
import com.loanservice.us4.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class LoanService {

    private static final BigDecimal LATE_FEE_PER_DAY = BigDecimal.valueOf(5);
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final LoanRepository loanRepository;

    @Autowired
    public LoanService(BookRepository bookRepository, UserRepository userRepository, LoanRepository loanRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.loanRepository = loanRepository;
    }

    public LoanRecord issueBook(LoanDTO loan) {
            Long userId = loan.getUserId();
            Long bookId = loan.getBookId();

            //Check the book by its id
            Book book = bookRepository.findById(bookId)
                    .orElseThrow(() -> new BookNotAvailableException("Book not found"));

            //Check if the book is available for a loan
            if (book.getStatus() != BookStatus.AVAILABLE) {
                throw new BookNotAvailableException("Book is not available for loan");
            }

            // Check if the user exists
            UserAccount user = userRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException("User not found"));

            // Check the user's loan limit
            int maxLoanLimit = 5;
            List<LoanRecord> userLoans = loanRepository.findByUserId(userId);
            if (userLoans.size() >= maxLoanLimit) {
                throw new LoanLimitExceededException("User has exceeded the max loan limit of " + maxLoanLimit);
            }
            //It sets the user, book, issue date (the current date), and due date (current date + 14 days).
            LoanRecord loanRecord = new LoanRecord();
            loanRecord.setUser(user);
            loanRecord.setBook(book);
            loanRecord.setIssueDate(LocalDate.now());
            loanRecord.setDueDate(LocalDate.now().plusDays(14));

            // Save the loan record and update the user's loans
            loanRecord = loanRepository.save(loanRecord); // Save and obtain the generated ID

            // Update book status to 'Loaned' and save changes
            book.setStatus(BookStatus.LOANED);
            bookRepository.save(book);

            // Add the loan record to the user's loans and save changes
            userLoans.add(loanRecord);
            userRepository.save(user);

            return loanRecord;
        }

    public BigDecimal returnBook(Long loanId) {
        LoanRecord loanRecord = loanRepository.findById(loanId)
                .orElseThrow(() -> new LoanNotFoundException("Loan not found"));

        LocalDate dueDate = loanRecord.getDueDate();
        LocalDate today = LocalDate.now();
        // Get the due date and the current date
        BigDecimal lateFee = BigDecimal.ZERO;

        if (today.isAfter(dueDate)) {
            long daysLate = ChronoUnit.DAYS.between(dueDate, today);
            // Calculate the late fee based on the number of days late and a daily rate
            lateFee = LATE_FEE_PER_DAY.multiply(BigDecimal.valueOf(daysLate));
            // // Set the late fee on the LoanRecord
            loanRecord.setLateFee(lateFee);
            loanRepository.save(loanRecord);

            UserAccount userAccount = loanRecord.getUser(); // Use the associated user
            if (userAccount != null) {
                // // Update the user's total late fees with the new late fee
                userAccount.setTotalLateFees(userAccount.getTotalLateFees().add(lateFee));
                userRepository.save(userAccount);
            } else {
                throw new UserNotFoundException("User not found");
            }
        }
        // Return the calculated late fee for this loan
        return loanRecord.getLateFee();
    }

    public List<LoanRecord> getUserLoans(Long userId) {
        List<LoanRecord> userLoans = loanRepository.findByUserId(userId);
        return userLoans;
    //To get the list of loan records
    }

    public void clearLateFees(Long userId) {
        // Retrieve all loans for the specified user
        List<LoanRecord> userLoans = loanRepository.findByUserId(userId);

        BigDecimal totalLateFees = BigDecimal.ZERO;

        // Calculate the total late fees for the user
        for (LoanRecord loan : userLoans) {
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
    }
}