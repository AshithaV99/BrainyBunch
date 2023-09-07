package com.loanservice.us4.Dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class UserDTO {
    private String username;
    private String email;
    private BigDecimal totalLateFees;
}
