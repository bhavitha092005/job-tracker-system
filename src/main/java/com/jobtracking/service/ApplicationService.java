package com.jobtracking.service;

import com.jobtracking.dto.application.ApplicationResponse;
import com.jobtracking.dto.application.UpdateStatusRequest;
import com.jobtracking.entity.JobApplication;
import com.jobtracking.entity.JobPosting;
import com.jobtracking.entity.User;
import com.jobtracking.entity.enums.ApplicationStatus;
import com.jobtracking.exception.BadRequestException;
import com.jobtracking.exception.ResourceNotFoundException;
import com.jobtracking.exception.UnauthorizedActionException;
import com.jobtracking.repository.ApplicationRepository;
import com.jobtracking.repository.JobPostingRepository;
import com.jobtracking.repository.UserRepository;
import com.jobtracking.security.CustomUserDetails;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ApplicationService {

    private static final long MAX_RESUME_SIZE = 5 * 1024 * 1024; // 5MB

    private final ApplicationRepository applicationRepository;
    private final JobPostingRepository jobPostingRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;

    public ApplicationService(ApplicationRepository applicationRepository,
                              JobPostingRepository jobPostingRepository,
                              UserRepository userRepository,
                              EmailService emailService) {

        this.applicationRepository = applicationRepository;
        this.jobPostingRepository = jobPostingRepository;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    // APPLY TO JOB
    @Transactional
    public void applyToJob(Long jobId,
                           MultipartFile resume,
                           CustomUserDetails userDetails) {

        JobPosting job = jobPostingRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found"));

        User candidate = getUser(userDetails.getId());

        validateResume(resume);
        String resumePath = storeResume(resume);

        JobApplication application = new JobApplication();
        application.setJobPosting(job);
        application.setCandidate(candidate);
        application.setResumePath(resumePath);
        application.setStatus(ApplicationStatus.APPLIED);

        applicationRepository.save(application);
    }

        // EMAIL NOTIFICATIONS
//        try {
//            emailService.send(
//                    candidate.getEmail(),
//                    "Application Submitted",
//                    "You successfully applied for: " + job.getTitle()
//            );
//
//            emailService.send(
//                    job.getCreatedBy().getEmail(),
//                    "New Application Received",
//                    "New candidate applied to: " + job.getTitle()
//            );
//
//        } catch (Exception e) {
//
//            e.printStackTrace();   
//        }
//    }

    // CANDIDATE APPLICATIONS
    public List<ApplicationResponse> getMyApplications(CustomUserDetails userDetails) {

        return applicationRepository.findByCandidateId(userDetails.getId())
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // HR APPLICATIONS
    public List<ApplicationResponse> getApplicationsForJob(Long jobId,
                                                           CustomUserDetails hrDetails) {

        JobPosting job = jobPostingRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found"));

        if (!job.getCreatedBy().getId().equals(hrDetails.getId())) {
            throw new UnauthorizedActionException("Not allowed");
        }

        return applicationRepository.findByJobPostingId(jobId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // UPDATE STATUS
    @Transactional
    public void updateStatus(Long applicationId,
                             UpdateStatusRequest request,
                             CustomUserDetails hrDetails) {

        JobApplication application = applicationRepository
                .findWithJobAndCreatorById(applicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found"));

        if (!application.getJobPosting().getCreatedBy().getId()
                .equals(hrDetails.getId())) {

            throw new UnauthorizedActionException(
                    "You are not allowed to modify this application"
            );
        }

        ApplicationStatus current = application.getStatus();
        ApplicationStatus next = request.getStatus();

        if (!isValidTransition(current, next)) {
            throw new BadRequestException("Invalid status transition");
        }

        application.setStatus(next);

        emailService.send(
                application.getCandidate().getEmail(),
                "Application Status Updated",
                "Your application for "
                        + application.getJobPosting().getTitle()
                        + " is now: " + next
        );
    }

    // DOWNLOAD RESUME
    @Transactional(readOnly = true)
    public ResponseEntity<Resource> downloadResume(Long applicationId,
                                                   CustomUserDetails hrDetails) {

        JobApplication application = applicationRepository
                .findWithJobAndCreatorById(applicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found"));

        if (!application.getJobPosting().getCreatedBy().getId()
                .equals(hrDetails.getId())) {
            throw new UnauthorizedActionException("Not allowed to download resume");
        }

        Path filePath = Path.of(application.getResumePath());

        if (!Files.exists(filePath)) {
            throw new ResourceNotFoundException("Resume file not found");
        }

        Resource resource = new FileSystemResource(filePath);

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + filePath.getFileName().toString() + "\""
                )
                .body(resource);
    }

    // HELPERS

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    private ApplicationResponse mapToResponse(JobApplication app) {
        return new ApplicationResponse(
                app.getId(),
                app.getCandidate().getFullName(),
                app.getJobPosting().getTitle(),
                app.getStatus(),
                app.getResumePath()
        );
    }

    // ✅ FIXED VALIDATION (Cloud Safe)
    private void validateResume(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new BadRequestException("Resume file is required");
        }

        if (file.getSize() > MAX_RESUME_SIZE) {
            throw new BadRequestException("Resume size must be <= 5MB");
        }

        String filename = file.getOriginalFilename();
        String contentType = file.getContentType();

        boolean validType =
                contentType != null &&
                (contentType.equalsIgnoreCase("application/pdf")
                || contentType.equalsIgnoreCase("application/octet-stream"));

        boolean validName =
                filename != null &&
                filename.toLowerCase().endsWith(".pdf");

        if (!validType && !validName) {
            throw new BadRequestException("Only PDF resumes are allowed");
        }
    }

    // ✅ HARDENED STORAGE LOGIC
    private String storeResume(MultipartFile file) {

        try {

            Path uploadDir = Path.of("uploads");

            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);   
            }

            String filename = UUID.randomUUID() + ".pdf";
            Path target = uploadDir.resolve(filename);

            Files.copy(file.getInputStream(), target);

            return target.toString();

        } catch (IOException e) {

            e.printStackTrace();   
            throw new RuntimeException("Failed to store resume");
        }
    }

    private boolean isValidTransition(ApplicationStatus current,
                                      ApplicationStatus next) {

        switch (current) {

            case APPLIED:
                return next == ApplicationStatus.REVIEWED;

            case REVIEWED:
                return next == ApplicationStatus.INTERVIEW;

            case INTERVIEW:
                return next == ApplicationStatus.HIRED
                        || next == ApplicationStatus.REJECTED;

            default:
                return false;
        }
    }
}
