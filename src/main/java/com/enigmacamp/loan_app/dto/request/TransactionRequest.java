package com.enigmacamp.loan_app.dto.request;

import com.enigmacamp.loan_app.constant.ApprovalStatus;
import com.enigmacamp.loan_app.entity.Customer;
import com.enigmacamp.loan_app.entity.InstalmentType;
import com.enigmacamp.loan_app.entity.LoanTransactionDetail;
import com.enigmacamp.loan_app.entity.LoanType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionRequest {
    private String id;
    private String customerId;
    private String loanTypeId;
    private Double nominal;
    private Long approvedAt;
    private String approvedBy;
    private ApprovalStatus approvalStatus;
    @JsonIgnore
    private List<LoanTransactionDetail> transactionDetails = new ArrayList<>(); //
    private Long createdAt;
    private Long updatedAt;
}
