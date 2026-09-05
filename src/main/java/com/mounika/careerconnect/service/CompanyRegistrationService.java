package com.mounika.careerconnect.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mounika.careerconnect.entity.Company;
import com.mounika.careerconnect.entity.User;
import com.mounika.careerconnect.repository.CompanyRepository;
import com.mounika.careerconnect.repository.UserRepository;

@Service
public class CompanyRegistrationService {

    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;

    public CompanyRegistrationService(
            UserRepository userRepository,
            CompanyRepository companyRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Company registerCompany(
            String username,
            String password,
            String companyName,
            String companyLocation,
            String roleName,
            String phone,
            String email) {

        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        if (companyRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Company email already exists");
        }

        User user = new User(
                username,
                passwordEncoder.encode(password),
                "COMPANY"
        );

        User savedUser = userRepository.save(user);

        Company company = new Company(
                savedUser,
                companyName,
                companyLocation,
                roleName,
                 email,
        phone
        );

        return companyRepository.save(company);
    }
public Company getCompanyByUsername(String username) {

    User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));

    return companyRepository.findByUserId(user.getId())
            .orElseThrow(() -> new RuntimeException("Company profile not found"));
}
}