package com.jobtracking.config;

import com.jobtracking.entity.Role;
import com.jobtracking.entity.User;
import com.jobtracking.entity.enums.RoleName;
import com.jobtracking.repository.RoleRepository;
import com.jobtracking.repository.UserRepository;

import jakarta.annotation.PostConstruct;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DataInitializer {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(RoleRepository roleRepository,
                           UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    @Transactional
    public void initRoles() {

        // Create roles
        for (RoleName roleName : RoleName.values()) {
            if (!roleRepository.existsByName(roleName)) {
                roleRepository.save(new Role(roleName));
            }
        }

        // Create admin user
        createAdminIfNotExists();
    }

    private void createAdminIfNotExists() {

        String adminEmail = "admin@jobtracker.com";

        if (userRepository.existsByEmail(adminEmail)) return;

        Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                .orElseThrow(() -> new RuntimeException("ROLE_ADMIN not found"));

        User admin = new User();
        admin.setFullName("System Admin");
        admin.setEmail(adminEmail);
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.getRoles().add(adminRole);

        userRepository.save(admin);

        System.out.println("✅ ADMIN USER CREATED");
        System.out.println("Email: admin@jobtracker.com");
        System.out.println("Password: admin123");
    }
}
