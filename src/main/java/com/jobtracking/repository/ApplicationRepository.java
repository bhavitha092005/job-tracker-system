package com.jobtracking.repository;

import com.jobtracking.entity.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<JobApplication, Long> {

    @EntityGraph(attributePaths = {"candidate", "jobPosting"})
    List<JobApplication> findByCandidateId(Long candidateId);
    

@EntityGraph(attributePaths = {"candidate", "jobPosting"})
List<JobApplication> findByJobPostingId(Long jobId);

@EntityGraph(attributePaths = {
        "jobPosting",
        "jobPosting.createdBy"
})
Optional<JobApplication> findWithJobAndCreatorById(Long id);
}
