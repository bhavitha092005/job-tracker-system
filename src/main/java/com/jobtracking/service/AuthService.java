package com.jobtracking.service;

import com.jobtracking.dto.auth.LoginRequest;
import com.jobtracking.dto.auth.LoginResponse;
import com.jobtracking.dto.auth.RegisterRequest;
import com.jobtracking.entity.Role;
import com.jobtracking.entity.User;
import com.jobtracking.entity.enums.RoleName;
import com.jobtracking.repository.RoleRepository;
import com.jobtracking.repository.UserRepository;
import com.jobtracking.security.JwtTokenProvider;

import jakarta.validation.Valid;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public AuthService(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder,
            JwtTokenProvider jwtTokenProvider,
            AuthenticationManager authenticationManager
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }

    // REGISTER CANDIDATE 

    public void registerCandidate(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        Role candidateRole = roleRepository.findByName(RoleName.ROLE_CANDIDATE)
                .orElseThrow(() -> new RuntimeException("ROLE_CANDIDATE not found"));

        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setRoles(Set.of(candidateRole));

        userRepository.save(user);

        System.out.println("✅ USER REGISTERED → " + user.getEmail());
    }

    // LOGIN 

    public LoginResponse login(@Valid LoginRequest request) {

        System.out.println("🔐 LOGIN ATTEMPT → " + request.getEmail());

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (Exception ex) {

            System.out.println("❌ AUTH FAILED → Bad credentials");
            ex.printStackTrace();   
            throw new RuntimeException("Bad credentials");
        }

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        
        System.out.println("RAW PASSWORD → " + request.getPassword());
        System.out.println("ENCODED PASSWORD → " + user.getPassword());
        System.out.println("MATCHES → " +
                passwordEncoder.matches(request.getPassword(), user.getPassword()));

        String token = jwtTokenProvider.generateToken(user);

        System.out.println("✅ LOGIN SUCCESS → " + user.getEmail());

        return new LoginResponse(token);
    }
}
