package com.loanservice.us4.Repository;

import com.loanservice.us4.Entity.LoanRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface LoanRepository extends JpaRepository<LoanRecord,Long> {
    List<LoanRecord> findByUserId(Long userId);
    List<LoanRecord> findByDueDate(LocalDate dueDate);

}
