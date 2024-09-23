package com.enigmacamp.loan_app.dto.request;

import com.enigmacamp.loan_app.constant.ApprovalStatus;
import com.enigmacamp.loan_app.entity.LoanTransactionDetail;
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
public class LoanTransactionRequest {
    private String id;
    private String customerId;
    private String loanTypeId;
    private String instalmentTypeId;
    private Double nominal;
    private String approvedBy;
    private Long approvedAt;
    private ApprovalStatus approvalStatus;
    private List<LoanTransactionDetail> transactionDetails = new ArrayList<>();
    private Long createdAt;
    private Long updatedAt;
}
