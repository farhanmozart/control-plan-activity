package com.mantap.dashboard.repository;

import com.mantap.dashboard.model.entity.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentEntity, String> {

    Optional<DepartmentEntity> findByDepartmentCode(String departmentCode);
}