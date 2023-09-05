package com.loanservice.us4.Entity;


import lombok.*;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    private String username;
    private String email;;
    private BigDecimal totalLateFees;

    @OneToMany(mappedBy = "user")
    private List<LoanRecord> loans;

}
