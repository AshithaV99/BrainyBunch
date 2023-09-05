package com.loanservice.us4.Repository;

import com.loanservice.us4.Entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book,Long> {
}
