package com.mantap.dashboard.service;

import com.mantap.dashboard.BusinessException;
import com.mantap.dashboard.model.dto.BaseResponse;
import com.mantap.dashboard.model.entity.DepartmentEntity;
import com.mantap.dashboard.model.entity.DivisionEntity;
import com.mantap.dashboard.model.entity.UsersEntity;
import com.mantap.dashboard.model.request.UserSignUpRequest;
import com.mantap.dashboard.model.response.AuthResponse.UserDto;
import com.mantap.dashboard.repository.DepartmentRepository;
import com.mantap.dashboard.repository.DivisionRepository;
import com.mantap.dashboard.repository.UsersRepository;
import com.mantap.dashboard.util.ResponseUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.mantap.dashboard.util.constant.Constant.*;
import static com.mantap.dashboard.util.constant.ResponseConstant.SIGNUP_SUCCESS_CODE;
import static com.mantap.dashboard.util.constant.ResponseConstant.SIGNUP_SUCCESS_MESSAGE;

@Service
@RequiredArgsConstructor
public class PostUserSignUpService extends ResponseUtil {
    private final UsersRepository usersRepository;
    private final DivisionRepository divisionRepository;
    private final DepartmentRepository departmentRepository;
    private final PasswordEncoder passwordEncoder;

    public static final String USER = "USER";

    @Transactional
    public BaseResponse<UserDto> execute(UserSignUpRequest input) {
        validateUserId(input);
        validateNip(input);
        validateEmail(input);

        DivisionEntity divisionEntity = divisionRepository.findByDivisionCode(input.getDivisionCode().toUpperCase()).orElseThrow(()
                -> new BusinessException(ERROR_CODE_10006, ERROR_TITLE_10006, ERROR_MESSAGE_10006));

        DepartmentEntity departmentEntity = departmentRepository.findByDepartmentCode(input.getDepartmentCode().toUpperCase()).orElseThrow(()
                -> new BusinessException(ERROR_CODE_10007, ERROR_TITLE_10007, ERROR_MESSAGE_10007));

        if (!Objects.equals(departmentEntity.getDivisionId(), divisionEntity.getDivisionId())) {
            throw new BusinessException(ERROR_CODE_10008, ERROR_TITLE_10008, ERROR_MESSAGE_10008);
        }

        constructUsers(input, divisionEntity, departmentEntity);

        UserDto response = UserDto.builder()
                .userId(input.getUserId())
                .nip(input.getNip())
                .name(input.getName())
                .role(USER)
                .division(divisionEntity.getDivisionName())
                .department(departmentEntity.getDepartmentName())
                .status("PENDING APPROVAL")
                .build();

        return ResponseUtil.success(SIGNUP_SUCCESS_CODE, SIGNUP_SUCCESS_MESSAGE, response);
    }

    private void constructUsers(UserSignUpRequest input, DivisionEntity divisionEntity,
                                DepartmentEntity departmentEntity) {
        UsersEntity users = new UsersEntity();
        users.setUserId(input.getUserId());
        users.setNip(input.getNip());
        users.setName(input.getName());
        users.setEmail(input.getEmail());
        users.setPassword(passwordEncoder.encode(input.getPassword()));
        users.setDivisionId(divisionEntity.getDivisionId());
        users.setDepartmentId(departmentEntity.getDepartmentId());
        users.setRole(USER);
        users.setPosition(input.getPosition());
        users.setIsActive(false);
        users.setTokenVersion(0L);
        usersRepository.save(users);
    }

    private void validateEmail(UserSignUpRequest input) {
        if (usersRepository.existsByEmail(input.getEmail())) {
            throw new BusinessException(ERROR_CODE_10005, ERROR_TITLE_10005, ERROR_MESSAGE_10005);
        }
    }

    private void validateNip(UserSignUpRequest input) {
        if (usersRepository.existsByNip(input.getNip())) {
            throw new BusinessException(ERROR_CODE_10004, ERROR_TITLE_10004, ERROR_MESSAGE_10004);
        }
    }

    private void validateUserId(UserSignUpRequest input) {
        if (usersRepository.existsByUserId(input.getUserId())) {
            throw new BusinessException(ERROR_CODE_10003, ERROR_TITLE_10003, ERROR_MESSAGE_10003);
        }
    }
}