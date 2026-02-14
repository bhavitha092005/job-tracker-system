package com.jobtracking.controller;

import com.jobtracking.dto.application.ApplicationResponse;
import com.jobtracking.dto.application.UpdateStatusRequest;
import com.jobtracking.security.CustomUserDetails;
import com.jobtracking.service.ApplicationService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    // APPLY TO JOB 
    @PreAuthorize("hasRole('CANDIDATE')")
    @PostMapping(
        value = "/apply",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public void apply(
            @RequestParam Long jobId,
            @RequestParam MultipartFile resume,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        applicationService.applyToJob(jobId, resume, userDetails);
    }


    //  CANDIDATE APPLICATIONS 
    @PreAuthorize("hasRole('CANDIDATE')")
    @GetMapping("/me")
    public List<ApplicationResponse> myApplications(
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        return applicationService.getMyApplications(userDetails);
    }

    // HR: APPLICATIONS FOR A JOB 
    @PreAuthorize("hasRole('HR')")
    @GetMapping("/job/{jobId}")
    public List<ApplicationResponse> applicationsForJob(
            @PathVariable Long jobId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        return applicationService.getApplicationsForJob(jobId, userDetails);
    }

    //  HR: UPDATE APPLICATION STATUS 
    @PreAuthorize("hasRole('HR')")
    @PatchMapping("/{applicationId}/status")
    public void updateStatus(
            @PathVariable Long applicationId,
            @Valid @RequestBody UpdateStatusRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        applicationService.updateStatus(applicationId, request, userDetails);
    }
    // DOWNLOAD RESUME
    @GetMapping("/resume/{applicationId}")
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity<Resource> downloadResume(
            @PathVariable Long applicationId,
            @AuthenticationPrincipal CustomUserDetails hrDetails) {

        return applicationService.downloadResume(applicationId, hrDetails);
    }

}
