package com.loanservice.us4.Entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
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
    @Column(name = "user_id",nullable=false)
    private Long id;

    private String username;
    private String email;
    private BigDecimal totalLateFees=BigDecimal.ZERO;




}
