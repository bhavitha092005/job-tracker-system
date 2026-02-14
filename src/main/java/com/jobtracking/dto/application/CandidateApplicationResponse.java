package com.jobtracking.dto.application;

import com.jobtracking.entity.enums.ApplicationStatus;

import java.time.LocalDateTime;

public class CandidateApplicationResponse {

    private Long id;
    private String jobTitle;
    private ApplicationStatus status;
    private LocalDateTime appliedAt;

    public CandidateApplicationResponse(Long id,
                                        String jobTitle,
                                        ApplicationStatus status,
                                        LocalDateTime appliedAt) {
        this.id = id;
        this.jobTitle = jobTitle;
        this.status = status;
        this.appliedAt = appliedAt;
    }

    public Long getId() {
        return id;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public LocalDateTime getAppliedAt() {
        return appliedAt;
    }
}
