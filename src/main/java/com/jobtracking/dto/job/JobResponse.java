package com.jobtracking.dto.job;


import java.math.BigDecimal;

public class JobResponse {

    private Long id;
    private String title;
    private String description;
    private BigDecimal salary;
    private String createdBy;

    public JobResponse(
            Long id,
            String title,
            String description,
            BigDecimal salary,
            String createdBy) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.salary = salary;
        this.createdBy = createdBy;
    }

    public Long getId() {
        return id;
    }
  
    public String getTitle() {
        return title;
    }
  
    public String getDescription() {
        return description;
    }
  
    public BigDecimal getSalary() {
        return salary;
    }
  
    public String getCreatedBy() {
        return createdBy;
    }
}