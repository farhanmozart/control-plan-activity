package com.mantap.dashboard.model.entity.tasks;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "TASK_ATTACHMENTS")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskAttachmentsEntity {
    @Id
    @Column(name = "TASK_ATTACHMENTS_ID")
    private String taskAttachmentsId;

    @Column(name = "TASK_ID")
    private String taskId;

    @Column(name = "FILE_NAME")
    private String fileName;

    @Column(name = "FILE_PATH")
    private String filePath;

    @Column(name = "UPLOADED_BY")
    private String uploadedBy;

    @Column(name = "UPLOADED_AT")
    private Timestamp uploadedAt;
}
