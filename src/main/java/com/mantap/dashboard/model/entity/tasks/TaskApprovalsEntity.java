package com.mantap.dashboard.model.entity.tasks;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "TASK_APPROVALS")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskApprovalsEntity {
    @Id
    @Column(name = "TASK_APPROVAL_ID")
    private String taskApprovalId;

    @Column(name = "TASK_ID")
    private String taskId;

    @Column(name = "APPROVER_ID")
    private String approverId;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "APPROVED_AT")
    private Timestamp approvedAt;

    @Column(name = "REMARKS")
    private String remarks;
}