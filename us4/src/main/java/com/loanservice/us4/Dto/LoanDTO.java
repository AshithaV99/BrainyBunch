package com.loanservice.us4.Dto;
import com.loanservice.us4.Entity.UserAccount;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
public class LoanDTO {

    @Column(nullable=false)
    private Long userId;
    @Column(nullable = false)
    private Long bookId;
    private LocalDate issueDate;



}

