package com.enigmacamp.loan_app.entity;

import com.enigmacamp.loan_app.constant.ApprovalStatus;
import com.enigmacamp.loan_app.constant.TransactionStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "trx_loan")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    private LoanType loanType;

    @ManyToOne
    private InstalmentType instalmentType;

    @ManyToOne
    private Customer customer;
    private Double nominal;
    private Long approvedAt;
    private String approvedBy;
    private ApprovalStatus approvalStatus;


    private Long createdAt;
    private Long updatedAt;

    @OneToMany (mappedBy = "loanTransaction", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<LoanTransactionDetail> loanTransactionDetails = new ArrayList<>();

    private TransactionStatus transactionStatus = TransactionStatus.OPEN;
    private Integer count;
}
