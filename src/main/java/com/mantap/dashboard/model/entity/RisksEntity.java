package com.mantap.dashboard.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "RISKS")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RisksEntity {
    @Id
    @Column(name = "RISK_ID")
    private String riskId;

    @Column(name = "PROJECT_ID")
    private String projectId;

    @Column(name = "RISK_NAME")
    private String riskName;

    @Column(name = "IMPACT")
    private Integer impact;

    @Column(name = "PROBABILITY")
    private Integer probability;

    @Column(name = "RISK_LEVEL")
    private String riskLevel;

    @Column(name = "MITIGATION")
    private String mitigation;

    @Column(name = "OWNER_ID")
    private String ownerId;

    @Column(name = "STATUS")
    private String status;
}
