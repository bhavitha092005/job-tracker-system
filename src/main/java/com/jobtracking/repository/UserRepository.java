package com.jobtracking.repository;

import com.jobtracking.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
	  @EntityGraph(attributePaths = {"roles"})
	    Optional<User> findByEmail(String email);

	    boolean existsByEmail(String email);
}