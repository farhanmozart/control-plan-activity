package com.mantap.dashboard.repository;

import com.mantap.dashboard.model.entity.UsersEntity;
import com.mantap.dashboard.model.projection.UsersProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<UsersEntity, String> {

    Optional<UsersEntity> findByNip(String nip);

    @Query(value = """
        SELECT
            u.USER_ID        AS userId,
            u.NIP            AS nip,
            u.NAME           AS name,
            d.DIVISION_NAME  AS division,
            dp.DEPARTMENT_NAME AS department,
            u.ROLE           AS role,
            u.IS_ACTIVE      AS isActive,
            u.PASSWORD       AS password,
            u.TOKEN_VERSION  AS tokenVersion
        FROM USERS u
        LEFT JOIN DIVISION d
            ON d.DIVISION_ID = u.DIVISION_ID
        LEFT JOIN DEPARTMENT dp
            ON dp.DEPARTMENT_ID = u.DEPARTMENT_ID
        WHERE u.NIP = :nip""", nativeQuery = true)
        Optional<UsersProjection> findUsersByNip(@Param("nip") String nip);

    boolean existsByUserId(String userId);
    boolean existsByNip(String nip);
    boolean existsByEmail(String email);
}