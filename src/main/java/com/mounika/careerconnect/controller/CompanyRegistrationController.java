package com.mounika.careerconnect.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;

import com.mounika.careerconnect.entity.Company;
import com.mounika.careerconnect.service.CompanyRegistrationService;
import com.mounika.careerconnect.dto.CompanyProfileDTO;
@RestController
@RequestMapping("/companies")
public class CompanyRegistrationController {

    private final CompanyRegistrationService companyRegistrationService;

    public CompanyRegistrationController(
            CompanyRegistrationService companyRegistrationService) {

        this.companyRegistrationService = companyRegistrationService;
    }

    @PostMapping("/register")
    public ResponseEntity<CompanyProfileDTO> registerCompany(
            @RequestBody Company company) {

        Company savedCompany = companyRegistrationService.registerCompany(
                company.getUser().getUsername(),
                company.getUser().getPassword(),
                company.getCompanyName(),
                company.getCompanyLocation(),
                company.getRoleName(),
                company.getPhone(),
                company.getEmail()
        );

        CompanyProfileDTO profile = new CompanyProfileDTO(
                savedCompany.getId(),
                savedCompany.getUser().getUsername(),
                savedCompany.getUser().getRole(),
                savedCompany.getCompanyName(),
                savedCompany.getCompanyLocation(),
                savedCompany.getRoleName(),
                savedCompany.getEmail(),
                savedCompany.getPhone()
        );

        return ResponseEntity.ok(profile);
    }
    @GetMapping("/me")
    public ResponseEntity<CompanyProfileDTO> getMyProfile(
            Authentication authentication) {
        String username = authentication.getName();

        Company company =
                companyRegistrationService.getCompanyByUsername(username);
        
        CompanyProfileDTO profile = new CompanyProfileDTO(
                company.getId(),
                company.getUser().getUsername(),
                company.getUser().getRole(),
                company.getCompanyName(),
                company.getCompanyLocation(),
                company.getRoleName(),
                company.getEmail(),
                company.getPhone()
        );


        return ResponseEntity.ok(profile);
    }
}