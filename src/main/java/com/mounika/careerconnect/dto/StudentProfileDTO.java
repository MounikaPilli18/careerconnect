package com.mounika.careerconnect.dto;

public class StudentProfileDTO {

    private Long id;
    private String username;
    private String role;
    private String name;
    private String phone;
    private String email;
    private String resume;
    private String location;
    private Double cgpa;

    public StudentProfileDTO(
            Long id,
            String username,
            String role,
            String name,
            String phone,
            String email,
            String resume,
            String location,
            Double cgpa) {

        this.id = id;
        this.username = username;
        this.role = role;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.resume = resume;
        this.location = location;
        this.cgpa = cgpa;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getResume() {
        return resume;
    }

    public String getLocation() {
        return location;
    }

    public Double getCgpa() {
        return cgpa;
    }
}