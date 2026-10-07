package com.mantap.dashboard.service;

import com.mantap.dashboard.model.dto.BaseResponse;
import com.mantap.dashboard.model.projection.UsersProjection;
import com.mantap.dashboard.model.response.PendingUserDto;
import com.mantap.dashboard.repository.UsersRepository;
import com.mantap.dashboard.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetPendingUsersService extends ResponseUtil {
    private final UsersRepository usersRepository;

    public BaseResponse<List<PendingUserDto>> execute() {
        List<UsersProjection> projections = usersRepository.findPendingUsers();

        List<PendingUserDto> response = projections.stream()
                .map(user ->
                        PendingUserDto.builder()
                                .userId(user.getUserId())
                                .nip(user.getNip())
                                .name(user.getName())
                                .email(user.getEmail())
                                .role(user.getRole())
                                .position(user.getPosition())
                                .division(user.getDivision())
                                .department(user.getDepartment())
                                .status("PENDING_APPROVAL")
                                .build()
                ).toList();
        return ResponseUtil.success(response);
    }
}