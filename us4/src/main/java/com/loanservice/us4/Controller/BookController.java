package com.loanservice.us4.Controller;

import com.loanservice.us4.Dto.BookDTO;
import com.loanservice.us4.Entity.Book;
import com.loanservice.us4.Service.BookService;
import com.loanservice.us4.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book")
public class BookController<book> {
    @Autowired
    private BookService bookService;

    @PostMapping
    public Book createBook(@RequestBody BookDTO bookDTO){
        return  bookService.saveBook(bookDTO);

    }



}
