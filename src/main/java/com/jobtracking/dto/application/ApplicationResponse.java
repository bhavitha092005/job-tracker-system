package com.jobtracking.dto.application;

import com.jobtracking.entity.enums.ApplicationStatus;

public class ApplicationResponse {

    private Long id;
    private String candidateName;
    private String jobTitle;
    private ApplicationStatus status;
    private String resumePath;

    public ApplicationResponse(
            Long id,
            String candidateName,
            String jobTitle,
            ApplicationStatus status,
            String resumePath) {
        this.id = id;
        this.candidateName = candidateName;
        this.jobTitle = jobTitle;
        this.status = status;
        this.resumePath = resumePath;
    }

    public Long getId() {
        return id;
    }

    public String getCandidateName() {
        return candidateName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public String getResumePath() {
        return resumePath;
    }
}