package com.loanservice.us4.Entity;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserAccount user;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;


    private LocalDate issueDate;
    private LocalDate dueDate;
    private BigDecimal lateFee;

}



