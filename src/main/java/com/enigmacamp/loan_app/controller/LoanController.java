package com.enigmacamp.loan_app.controller;

import com.enigmacamp.loan_app.constant.ApprovalStatus;
import com.enigmacamp.loan_app.constant.ERole;
import com.enigmacamp.loan_app.constant.LoanStatus;
import com.enigmacamp.loan_app.constant.PathApi;
import com.enigmacamp.loan_app.dto.request.LoanTransactionRequest;
import com.enigmacamp.loan_app.dto.response.LoanTransactionDetailResponse;
import com.enigmacamp.loan_app.dto.response.LoanTransactionResponse;
import com.enigmacamp.loan_app.entity.LoanTransaction;
import com.enigmacamp.loan_app.entity.LoanTransactionDetail;
import com.enigmacamp.loan_app.service.LoanService;
import com.enigmacamp.loan_app.service.LoanTransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(PathApi.LOAN)
@RequiredArgsConstructor
@Slf4j
public class LoanController {
    private final LoanService loanService;
    private final LoanTransactionService loanTransactionService;

    @PostMapping
    public ResponseEntity<LoanTransactionResponse> addOneMonthLoanTransaction(@RequestBody LoanTransactionRequest request) {
        try {
            LoanTransaction transaction = loanTransactionService.addLoanTransaction(request);
            LoanTransactionResponse response = convertToResponse(transaction);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanTransactionResponse> getLoanTransactionById(@PathVariable String id) {
        LoanTransaction transaction = loanTransactionService.getLoanById(id);
        return ResponseEntity.ok(convertToResponse(transaction));
    }



    private LoanTransactionResponse convertToResponse(LoanTransaction transaction) {
        List<LoanTransactionDetailResponse> loanTransactionDetailRespons = transaction.getLoanTransactionDetails().stream()
                .map(this::converToLoanTransactionDetail).toList();

        return LoanTransactionResponse.builder()
                .id(transaction.getId())
                .customerId(transaction.getCustomer().getId())
                .loanTypeId(transaction.getLoanType().getId())
                .instalmentTypeId(transaction.getInstalmentType().getId())
                .nominal(transaction.getNominal())
                .approvedAt(transaction.getApprovedAt())
                .approvalStatus(transaction.getApprovalStatus())
                .createdAt(transaction.getCreatedAt())
                .updatedAt(transaction.getUpdatedAt())
                .approvedBy(transaction.getApprovedBy())
                .transactionDetails(loanTransactionDetailRespons)
                .build();
    }

    private LoanTransactionDetailResponse converToLoanTransactionDetail(LoanTransactionDetail detail) {
        return LoanTransactionDetailResponse.builder()
                .id(detail.getId())
                .transactionDate(detail.getTransactionDate())
                .nominal(detail.getNominal())
                .loanStatus(detail.getLoanStatus().name())
                .createdAt(detail.getCreatedAt())
                .updatedAt(detail.getUpdatedAt())
                .build();
    }
}
