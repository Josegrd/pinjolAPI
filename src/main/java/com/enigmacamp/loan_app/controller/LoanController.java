package com.enigmacamp.loan_app.controller;

import com.enigmacamp.loan_app.constant.PathApi;
import com.enigmacamp.loan_app.dto.request.TransactionRequest;
import com.enigmacamp.loan_app.dto.response.TransactionResponse;
import com.enigmacamp.loan_app.entity.LoanTransaction;
import com.enigmacamp.loan_app.entity.LoanTransactionDetail;
import com.enigmacamp.loan_app.entity.LoanType;
import com.enigmacamp.loan_app.service.LoanService;
import com.enigmacamp.loan_app.service.LoanTransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(PathApi.LOAN)
@RequiredArgsConstructor
@Slf4j
public class LoanController {
    private final LoanService loanService;
    private final LoanTransactionService loanTransactionService;

    @PostMapping
    public ResponseEntity<TransactionResponse> addOneMonthLoanTransaction(@RequestBody TransactionRequest request) {
        try {
            LoanTransaction transaction = loanTransactionService.addLoanTransaction(request);
            TransactionResponse response = convertToResponse(transaction);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    private TransactionResponse convertToResponse(LoanTransaction transaction) {
        List<TransactionResponse> transactionDetailResponses = transaction.getLoanTransactionDetails().stream().map(this::convertToResponse).toList();

        return TransactionResponse.builder()
                .id(transaction.getId())
                .customerId(transaction.getCustomer().getId())
                .loanTypeId(transaction.getLoanType().getId())
                .instalmentTypeId(transaction.getInstalmentType().getId())
                .nominal(transaction.getNominal())
                .approvedAt(System.currentTimeMillis())
                .createdAt(System.currentTimeMillis())
                .updatedAt(System.currentTimeMillis())
                .approvedBy(transaction.getApprovedBy())
                .build();
    }

    private TransactionResponse convertToResponse(LoanTransactionDetail detail) {
        return TransactionResponse.builder()
                .id(detail.getId())
                .nominal(detail.getNominal())
                .build();
    }
}
