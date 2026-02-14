package com.jobtracking.repository;

import com.jobtracking.entity.JobPosting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface JobPostingRepository extends JpaRepository<JobPosting, Long> {

    @EntityGraph(attributePaths = {"createdBy"})
    Page<JobPosting> findAll(Pageable pageable);
    
    @EntityGraph(attributePaths = {"createdBy"})
    Page<JobPosting> findByCreatedById(Long createdById, Pageable pageable);
}
