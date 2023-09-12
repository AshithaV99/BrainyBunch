package com.loanservice.us4.Controller;

import com.loanservice.us4.Dto.UserDTO;
import com.loanservice.us4.Entity.UserAccount;
import com.loanservice.us4.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO){
        try{  userService.saveUser(userDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);}
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public Optional<UserAccount> getUserById(@PathVariable Long id)  {
        return  userService.findUserById(id);
    }


}
