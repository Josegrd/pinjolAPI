package com.enigmacamp.loan_app.service;

import com.enigmacamp.loan_app.dto.request.ApproveTransactionRequest;
import com.enigmacamp.loan_app.dto.request.LoanTransactionRequest;
import com.enigmacamp.loan_app.dto.response.LoanTransactionResponse;
import com.enigmacamp.loan_app.entity.LoanTransaction;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface LoanTransactionService {
    LoanTransaction addLoanTransaction(LoanTransactionRequest request);
    List<LoanTransaction> getAllLoanTransactions();
    LoanTransaction getLoanById(String id);
    void deleteById(String id);
    LoanTransactionResponse approveTransactionLoan(ApproveTransactionRequest request, String adminId);
    String payInstallment(String id);

}
