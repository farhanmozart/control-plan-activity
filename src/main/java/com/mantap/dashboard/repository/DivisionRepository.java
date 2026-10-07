package com.mantap.dashboard.repository;

import com.mantap.dashboard.model.entity.DivisionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DivisionRepository extends JpaRepository<DivisionEntity, String> {
    Optional<DivisionEntity> findByDivisionCode(String divisionCode);
}