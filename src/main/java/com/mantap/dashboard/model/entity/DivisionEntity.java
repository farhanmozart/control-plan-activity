package com.mantap.dashboard.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "DIVISION")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DivisionEntity {
    @Id
    @Column(name = "DIVISION_ID")
    private String divisionId;

    @Column(name = "DIVISION_CODE")
    private String divisionCode;

    @Column(name = "DIVISION_NAME")
    private String divisionName;
}