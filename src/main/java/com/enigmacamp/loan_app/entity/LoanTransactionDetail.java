package com.enigmacamp.loan_app.entity;

import com.enigmacamp.loan_app.constant.LoanStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "trx_loan_detail")
public class LoanTransactionDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private Long transactionDate;
    private Double nominal;
    @ManyToOne
    @JoinColumn(name = "loan_transaction_id")
    private LoanTransaction loanTransaction;
    private LoanStatus loanStatus; // enum
    private Long createdAt;
    private Long updatedAt;
}
