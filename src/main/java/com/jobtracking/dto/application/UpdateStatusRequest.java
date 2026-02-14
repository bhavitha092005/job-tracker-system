package com.jobtracking.dto.application;

import com.jobtracking.entity.enums.ApplicationStatus;
import jakarta.validation.constraints.NotNull;

public class UpdateStatusRequest {

    @NotNull
    private ApplicationStatus status;

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }
}