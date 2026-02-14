package com.jobtracking.repository;

import com.jobtracking.entity.Role;
import com.jobtracking.entity.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RoleName name);

    boolean existsByName(RoleName name);
}