package com.enigmacamp.loan_app.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
@Table(name = "mst_admin")
@Builder
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String email;

    @OneToOne
    @JoinColumn(name = "user_id_id")
    private User userId;
}
