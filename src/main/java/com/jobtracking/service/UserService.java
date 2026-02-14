package com.jobtracking.service;

import com.jobtracking.dto.auth.RegisterRequest;
import com.jobtracking.entity.Role;
import com.jobtracking.entity.User;
import com.jobtracking.entity.enums.RoleName;
import com.jobtracking.exception.ResourceNotFoundException;
import com.jobtracking.repository.RoleRepository;
import com.jobtracking.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void createHr(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }

        Role hrRole = roleRepository.findByName(RoleName.ROLE_HR)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));

        User user = new User();
        user.setEmail(request.getEmail());
        user.setFullName(request.getFullName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.getRoles().add(hrRole);

        userRepository.save(user);
    }
}
