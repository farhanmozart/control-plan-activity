package com.mantap.dashboard.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "USERS")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsersEntity {
    @Id
    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "NIP")
    private String nip;

    @Column(name = "NAME")
    private String name;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "DIVISION_ID")
    private String divisionId;

    @Column(name = "DEPARTMENT_ID")
    private String departmentId;

    @Column(name = "ROLE")
    private String role;

    @Column(name = "POSITION")
    private String position;

    @Column(name = "IS_ACTIVE")
    private Boolean isActive;

    @Column(name = "TOKEN_VERSION")
    private Long tokenVersion;

}
