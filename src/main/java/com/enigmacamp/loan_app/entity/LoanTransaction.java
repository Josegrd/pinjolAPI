package com.enigmacamp.loan_app.entity;

import com.enigmacamp.loan_app.constant.ApprovalStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
//    @JoinColumn(name = "loan_type_id")
    private LoanType loanType;

    @ManyToOne
//    @JoinColumn(name = "instalment_type_id")
    private InstalmentType instalmentType;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    private Double nominal;
    private Long approvedAt;
    private String approvedBy;
    private Long rejectedAt;
    private String rejectedBy;
    private ApprovalStatus approvalStatus; // enum
    private Long createdAt;
    private Long updatedAt;

    @OneToMany
    private List<LoanTransactionDetail> loanTransactionDetails;
}
