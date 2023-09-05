package com.loanservice.us4.Dto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
public class LoanDTO {
    private Long userId;

    private Long bookId;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private BigDecimal lateFee;
}
