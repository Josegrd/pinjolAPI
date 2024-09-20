package com.enigmacamp.loan_app.repository;

import com.enigmacamp.loan_app.entity.LoanTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanTransactionRepository extends JpaRepository<LoanTransaction, String> {
}
