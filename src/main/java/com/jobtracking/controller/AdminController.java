package com.jobtracking.controller;


import com.jobtracking.dto.auth.RegisterRequest;
import com.jobtracking.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create-hr")
    public ResponseEntity<?> createHr(@Valid @RequestBody RegisterRequest request) {
        userService.createHr(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
