package com.mounika.careerconnect.dto;

public class CompanyProfileDTO {

    private Long id;
    private String username;
    private String role;
    private String companyName;
    private String companyLocation;
    private String roleName;
    private String email;
    private String phone;

    public CompanyProfileDTO(
            Long id,
            String username,
            String role,
            String companyName,
            String companyLocation,
            String roleName,
            String email,
            String phone) {

        this.id = id;
        this.username = username;
        this.role = role;
        this.companyName = companyName;
        this.companyLocation = companyLocation;
        this.roleName = roleName;
        this.email = email;
        this.phone = phone;
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

    public String getCompanyName() {
        return companyName;
    }

    public String getCompanyLocation() {
        return companyLocation;
    }

    public String getRoleName() {
        return roleName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }
}