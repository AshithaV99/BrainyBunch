package com.loanservice.us4.Dto;

import com.loanservice.us4.Entity.BookStatus;
import com.loanservice.us4.Entity.UserAccount;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BookDTO {
    private String title;
    private String ISBN;
    private BookStatus status;

}
