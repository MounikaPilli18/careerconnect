package com.mounika.careerconnect.dto;

public class ApplicantDTO {

    private Long applicationId;
    private String studentName;
    private String email;
    private String phone;
    private String location;
    private Double cgpa;
    private String resume;
    private String status;

    public ApplicantDTO() {
    }

    public ApplicantDTO(
            Long applicationId,
            String studentName,
            String email,
            String phone,
            String location,
            Double cgpa,
            String resume,
            String status) {

        this.applicationId = applicationId;
        this.studentName = studentName;
        this.email = email;
        this.phone = phone;
        this.location = location;
        this.cgpa = cgpa;
        this.resume = resume;
        this.status = status;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getLocation() {
        return location;
    }

    public Double getCgpa() {
        return cgpa;
    }

    public String getResume() {
        return resume;
    }

    public String getStatus() {
        return status;
    }
}