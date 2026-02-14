package com.jobtracking.dto.user;

import java.util.Set;

public class UserResponse {

    private Long id;
    private String fullName;
    private String email;
    private Set<String> roles;

    public UserResponse(Long id, String fullName, String email, Set<String> roles) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }
  
    public String getEmail() {
        return email;
    }
  
    public Set<String> getRoles() {
        return roles;
    }
}
