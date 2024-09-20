package com.enigmacamp.loan_app.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Entity
@Data
@Builder
@Table(name = "mst_customer")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private String phone;
    private String status;

    @OneToOne
    private User userId;

    private Boolean isActive;
}
