package com.mantap.dashboard.repository;

import com.mantap.dashboard.model.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<UsersEntity, String> {

    @Query
    Optional<UsersEntity> findByNip(@Param("nip") String nip);
}