package com.enigmacamp.loan_app.service;

import com.enigmacamp.loan_app.entity.LoanType;

import java.util.List;

public interface LoanService {
    LoanType addLoan(LoanType loan);
    List<LoanType> getAllLoan();
    LoanType updateLoan(LoanType loan);
    void deleteById(String id);
    LoanType getLoanTypeById(String id);
}
