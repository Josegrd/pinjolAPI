package com.enigmacamp.loan_app.controller;

import com.enigmacamp.loan_app.constant.PathApi;
import com.enigmacamp.loan_app.dto.request.ApproveTransactionRequest;
import com.enigmacamp.loan_app.dto.request.LoanTransactionRequest;
import com.enigmacamp.loan_app.dto.response.CommonResponse;
import com.enigmacamp.loan_app.dto.response.LoanTransactionDetailResponse;
import com.enigmacamp.loan_app.dto.response.LoanTransactionResponse;
import com.enigmacamp.loan_app.entity.LoanTransaction;
import com.enigmacamp.loan_app.entity.LoanTransactionDetail;
import com.enigmacamp.loan_app.service.LoanService;
import com.enigmacamp.loan_app.service.LoanTransactionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(PathApi.LOAN)
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@Slf4j
public class LoanController {
    private final LoanService loanService;
    private final LoanTransactionService loanTransactionService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    public ResponseEntity<LoanTransactionResponse> addLoanTransaction(@RequestBody LoanTransactionRequest request) {
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


    @GetMapping
    public ResponseEntity<List<LoanTransactionResponse>> getAllLoanTransaction() {
        List<LoanTransaction> transactions = loanTransactionService.getAllLoanTransactions();

        List<LoanTransactionResponse> responses = transactions.stream().map(this::convertToResponse).toList();
        return ResponseEntity.ok(responses);
    }

    @PutMapping("approve/{adminId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<LoanTransactionResponse> approveLoanTransaction(@PathVariable String adminId, @RequestBody ApproveTransactionRequest request) {
        LoanTransactionResponse transaction = loanTransactionService.approveTransactionLoan(request, adminId);
        return ResponseEntity.ok(transaction);
    }

    @PutMapping("reject/{adminId}")
    public ResponseEntity<LoanTransactionResponse> rejectLoanTransaction(@PathVariable String adminId, @RequestBody ApproveTransactionRequest request) {
        LoanTransactionResponse transaction = loanTransactionService.approveTransactionLoan(request, adminId);
        return ResponseEntity.ok(transaction);
    }

    @PutMapping("/{trxId}/pay")
    public ResponseEntity<?> payInstallment(@PathVariable String trxId) {
        String response = loanTransactionService.payInstallment(trxId);
        CommonResponse commonResponse = new CommonResponse(HttpStatus.OK.value(),"Installment has been paid", response);
        return ResponseEntity.ok(commonResponse);
    }

    private LoanTransactionResponse convertToResponse(LoanTransaction transaction) {
        List<LoanTransactionDetailResponse> loanTransactionDetailRespons =
                transaction.getLoanTransactionDetails() != null && !transaction.getLoanTransactionDetails().isEmpty()
                        ? transaction.getLoanTransactionDetails().stream()
                        .map(this::converToLoanTransactionDetail)
                        .toList()
                        : new ArrayList<>();

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
