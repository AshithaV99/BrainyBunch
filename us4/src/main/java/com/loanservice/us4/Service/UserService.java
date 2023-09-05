package com.loanservice.us4.Service;

import com.loanservice.us4.Dto.UserDTO;
import com.loanservice.us4.Entity.UserAccount;
import com.loanservice.us4.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void saveUser(UserDTO userDTO) {
        UserAccount user= new UserAccount();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setTotalLateFees(userDTO.getTotalLateFees());
        userRepository.save(user);

    }
}
