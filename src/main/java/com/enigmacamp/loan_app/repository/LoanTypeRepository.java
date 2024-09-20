package com.enigmacamp.loan_app.repository;

import com.enigmacamp.loan_app.entity.LoanType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanTypeRepository extends JpaRepository<LoanType, String> {
}
