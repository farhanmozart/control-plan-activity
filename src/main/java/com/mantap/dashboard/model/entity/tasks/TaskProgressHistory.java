package com.mantap.dashboard.model.entity.tasks;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "TASK_PROGRESS_HISTORY")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskProgressHistory {
    @Id
    @Column(name = "TASK_PROGRESS_ID")
    private String taskProgressId;

    @Column(name = "TASK_ID")
    private String taskId;

    @Column(name = "OLD_PROGRESS")
    private String oldProgress;

    @Column(name = "NEW_PROGRESS")
    private String newProgress;

    @Column(name = "UPDATED_BY")
    private String updatedBy;

    @Column(name = "UPDATED_AT")
    private Timestamp updatedAt;

    @Column(name = "REMARKS")
    private String remarks;
}
