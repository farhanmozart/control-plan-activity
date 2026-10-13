package com.mantap.dashboard.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetAuthSessionResponse {
    private Boolean authenticated;
    private String userId;
    private String nip;
    private String name;
    private String role;
    private String division;
    private String department;
}