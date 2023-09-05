package com.loanservice.us4.Service;

import com.loanservice.us4.Dto.BookDTO;
import com.loanservice.us4.Entity.Book;
import com.loanservice.us4.Entity.BookStatus;
import com.loanservice.us4.Repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;


    public Book saveBook(BookDTO bookDTO) {
        Book savedBook = new Book();
        savedBook.setUser_id(bookDTO.getUser_id());
        savedBook.setTitle(bookDTO.getTitle());
        savedBook.setISBN(bookDTO.getISBN());
        savedBook.setStatus(BookStatus.valueOf(bookDTO.getStatus()));
        return bookRepository.save(savedBook);
    }


}