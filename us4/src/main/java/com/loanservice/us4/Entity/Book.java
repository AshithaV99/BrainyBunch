package com.loanservice.us4.Entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    @JoinColumn(name ="user_id",nullable = false)
    private Long user_id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String ISBN;

    @Enumerated(value = EnumType.STRING)
    private BookStatus status;
}


