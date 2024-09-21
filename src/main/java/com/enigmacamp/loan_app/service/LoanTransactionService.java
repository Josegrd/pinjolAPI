package com.enigmacamp.loan_app.service;

import com.enigmacamp.loan_app.dto.request.LoanTransactionRequest;
import com.enigmacamp.loan_app.entity.LoanTransaction;

import java.util.List;

public interface LoanTransactionService {
    LoanTransaction addLoanTransaction(LoanTransactionRequest request);
    List<LoanTransaction> getAllLoanTransactions();
    LoanTransaction getLoanById(String id);
    void deleteById(String id);
}
