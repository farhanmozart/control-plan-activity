package com.mantap.dashboard.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "DEPARTMENT")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DepartmentEntity {

    @Id
    @Column(name = "DEPARTMENT_ID")
    private String departmentId;

    @Column(name = "DIVISION_ID")
    private String divisionId;

    @Column(name = "DEPARTMENT_CODE")
    private String departmentCode;

    @Column(name = "DEPARTMENT_NAME")
    private String departmentName;
}
