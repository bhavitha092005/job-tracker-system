package com.jobtracking.dto.job;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class JobCreateRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotNull
    private BigDecimal salary;

    public String getTitle() {
        return title;
    }
  
    public void setTitle(String title) {
        this.title = title;
    }
  
    public String getDescription() {
        return description;
    }
  
    public void setDescription(String description) {
        this.description = description;
    }
  
    public BigDecimal getSalary() {
        return salary;
    }
  
    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }
}
