package com.mantap.dashboard.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "PROGRAMS")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProgramsEntity {
    @Id
    @Column(name = "PROGRAM_ID")
    private String programId;

    @Column(name = "PROGRAM_CODE")
    private String programCode;

    @Column(name = "PROGRAM_NAME")
    private String programName;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "OWNER_ID")
    private String ownerId;

    @Column(name = "START_DATE")
    private Timestamp startDate;

    @Column(name = "END_DATE")
    private Timestamp endDate;

    @Column(name = "STATUS")
    private String status;
}
