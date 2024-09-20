package com.enigmacamp.loan_app.dto.response;

import com.enigmacamp.loan_app.constant.ApprovalStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionResponse {

    private String id;
    private String loanTypeId;
    private String instalmentTypeId;
    private String customerId;
    private Double nominal;
    private Long approvedAt;
    private String approvedBy;
    private ApprovalStatus approvalStatus;
    private List<TransactionDetailResponse> transactionDetailResponses;
    private Long createdAt;
    private Long updatedAt;
}
