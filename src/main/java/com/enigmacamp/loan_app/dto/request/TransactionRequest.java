package com.enigmacamp.loan_app.dto.request;

import com.enigmacamp.loan_app.entity.Customer;
import com.enigmacamp.loan_app.entity.InstalmentType;
import com.enigmacamp.loan_app.entity.LoanType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionRequest {
    private LoanType loanType;
    private InstalmentType instalmentType;
    private Customer customer;
    private Double nominal;
}
