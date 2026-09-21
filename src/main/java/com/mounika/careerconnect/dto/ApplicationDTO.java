package com.mounika.careerconnect.dto;

import java.time.LocalDateTime;

public class ApplicationDTO {

    private Long applicationId;
    private int jobId;
    private String jobTitle;
    private String companyName;
    private String location;
    private double salary;
    private LocalDateTime appliedAt;
    private String status;

    public ApplicationDTO() {
    }

    public ApplicationDTO(
            Long applicationId,
            int jobId,
            String jobTitle,
            String companyName,
            String location,
            double salary,
            LocalDateTime appliedAt,
            String status) {

        this.applicationId = applicationId;
        this.jobId = jobId;
        this.jobTitle = jobTitle;
        this.companyName = companyName;
        this.location = location;
        this.salary = salary;
        this.appliedAt = appliedAt;
        this.status = status;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public int getJobId() {
        return jobId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getLocation() {
        return location;
    }

    public double getSalary() {
        return salary;
    }

    public LocalDateTime getAppliedAt() {
        return appliedAt;
    }

    public String getStatus() {
        return status;
    }
}