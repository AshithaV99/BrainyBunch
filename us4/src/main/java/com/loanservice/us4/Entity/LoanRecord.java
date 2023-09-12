package com.loanservice.us4.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.springframework.cglib.core.Local;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "loan_id",nullable = false)
    private Long id;

    private Long userId;
    private Long bookId;

    @Column(name = "issue_date")
    private LocalDate issueDate;


    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "late_fee")
    private BigDecimal lateFee;

    @Column(name = "return_date")
    private LocalDate returnDate;

}



