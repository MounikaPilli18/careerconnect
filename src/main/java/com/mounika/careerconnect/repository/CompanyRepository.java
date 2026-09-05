package com.mounika.careerconnect.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mounika.careerconnect.entity.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    Optional<Company> findByUserId(Long userId);

    Optional<Company> findByEmail(String email);
}