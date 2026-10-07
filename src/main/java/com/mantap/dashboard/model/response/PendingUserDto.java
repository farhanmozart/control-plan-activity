package com.mantap.dashboard.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PendingUserDto {
    private String userId;
    private String nip;
    private String name;
    private String email;
    private String role;
    private String position;
    private String division;
    private String department;
    private String status;
}
