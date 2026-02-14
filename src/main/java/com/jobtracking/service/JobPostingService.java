package com.jobtracking.service;

import com.jobtracking.dto.job.JobCreateRequest;
import com.jobtracking.dto.job.JobResponse;
import com.jobtracking.entity.User;
import com.jobtracking.exception.ResourceNotFoundException;
import com.jobtracking.repository.JobPostingRepository;
import com.jobtracking.repository.UserRepository;
import com.jobtracking.security.CustomUserDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.jobtracking.entity.JobPosting;

@Service
public class JobPostingService {

    private final JobPostingRepository jobPostingRepository;
    private final UserRepository userRepository;

    public JobPostingService(
            JobPostingRepository jobPostingRepository,
            UserRepository userRepository) {
        this.jobPostingRepository = jobPostingRepository;
        this.userRepository = userRepository;
    }

   
    public void createJob(
            JobCreateRequest request,
            CustomUserDetails userDetails) {

        User hr = userRepository.findById(userDetails.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("HR user not found"));

        JobPosting job = new JobPosting();
        job.setTitle(request.getTitle());
        job.setDescription(request.getDescription());
        job.setSalary(request.getSalary());
        job.setCreatedBy(hr);

        jobPostingRepository.save(job);
    }

    public Page<JobResponse> listJobs(Pageable pageable) {

        return jobPostingRepository.findAll(pageable)
                .map(job -> new JobResponse(
                        job.getId(),
                        job.getTitle(),
                        job.getDescription(),
                        job.getSalary(),
                        job.getCreatedBy().getFullName()
                ));
    }

   
    public JobResponse getJobById(Long jobId) {

        JobPosting job = jobPostingRepository.findById(jobId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Job not found"));

        return new JobResponse(
                job.getId(),
                job.getTitle(),
                job.getDescription(),
                job.getSalary(),
                job.getCreatedBy().getFullName()
        );
    }
    public Page<JobResponse> listHrJobs(CustomUserDetails userDetails, Pageable pageable) {

        return jobPostingRepository.findByCreatedById(userDetails.getId(), pageable)
                .map(job -> new JobResponse(
                        job.getId(),
                        job.getTitle(),
                        job.getDescription(),
                        job.getSalary(),
                        job.getCreatedBy().getFullName()
                ));
    }

}
