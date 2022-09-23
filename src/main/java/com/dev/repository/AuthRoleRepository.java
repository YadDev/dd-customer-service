package com.dev.repository;

import com.dev.constants.EAuthRole;
import com.dev.entities.AuthRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRoleRepository extends JpaRepository<AuthRole, Long> {
    Optional<AuthRole> findByName(EAuthRole name);
}
