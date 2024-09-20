package com.enigmacamp.loan_app.entity;

import com.enigmacamp.loan_app.constant.EInstalmentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "t_instalment_type")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InstalmentType {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private EInstalmentType instalmentType;
}
