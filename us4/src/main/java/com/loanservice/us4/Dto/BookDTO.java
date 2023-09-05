package com.loanservice.us4.Dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BookDTO {
    private Long user_id;
    private String title;
    private String ISBN;
    private String status;
}
