package com.loanservice.us4.Dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class LoanReturnDTO {
    private Long id;
    private LocalDate returnDate;


}
