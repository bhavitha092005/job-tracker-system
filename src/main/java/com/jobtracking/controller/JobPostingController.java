package com.jobtracking.controller;


import com.jobtracking.dto.job.JobCreateRequest;
import com.jobtracking.dto.job.JobResponse;
import com.jobtracking.security.CustomUserDetails;
import com.jobtracking.service.JobPostingService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/jobs")
public class JobPostingController {

    private final JobPostingService jobPostingService;

    public JobPostingController(JobPostingService jobPostingService) {
        this.jobPostingService = jobPostingService;
    }

    @PreAuthorize("hasRole('HR')")
    @PostMapping
    public void createJob(
            @Valid @RequestBody JobCreateRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        jobPostingService.createJob(request, userDetails);
    }

    @GetMapping
    public Page<JobResponse> listJobs(Pageable pageable) {
        return jobPostingService.listJobs(pageable);
    }
    @PreAuthorize("hasRole('HR')")
    @GetMapping("/my-jobs")
    public Page<JobResponse> listMyJobs(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            Pageable pageable) {

        return jobPostingService.listHrJobs(userDetails, pageable);
    }

}