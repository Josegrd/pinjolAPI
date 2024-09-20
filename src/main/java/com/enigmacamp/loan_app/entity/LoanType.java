package com.enigmacamp.loan_app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "t_loan_type")
public class LoanType {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private Double maxLoan;
    private String type;

    @OneToOne
    private Customer customer;
}
