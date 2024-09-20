package com.enigmacamp.loan_app.service;

import com.enigmacamp.loan_app.dto.request.TransactionRequest;
import com.enigmacamp.loan_app.entity.LoanTransaction;

import java.util.List;

public interface LoanTransactionService {
    LoanTransaction addLoanTransaction(TransactionRequest request);
    List<LoanTransaction> getAllLoanTransactions();
    LoanTransaction getLoanById(String id);
    void deleteById(String id);
}
