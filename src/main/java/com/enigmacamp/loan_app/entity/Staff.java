package com.enigmacamp.loan_app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "mst_staff")
@Builder
@Data
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String email;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User userId;
}
