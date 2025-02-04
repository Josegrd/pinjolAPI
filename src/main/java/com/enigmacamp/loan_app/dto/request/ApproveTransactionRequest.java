package com.enigmacamp.loan_app.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApproveTransactionRequest {
    private String loanTransactionId;
    private Integer interestRates;

}
