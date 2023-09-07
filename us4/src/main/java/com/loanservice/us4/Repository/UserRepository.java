package com.loanservice.us4.Repository;

import com.loanservice.us4.Entity.LoanRecord;
import com.loanservice.us4.Entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserAccount,Long> {

}
