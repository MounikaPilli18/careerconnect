package com.mounika.careerconnect.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mounika.careerconnect.entity.Job;

public interface JobRepository extends JpaRepository<Job, Integer> {
	List<Job> findByJobTitle(String jobTitle);
	List<Job> findBySalaryGreaterThanEqual(double salary);
	List<Job> findByCompanyName(String companyName);
	List<Job> findByLocation(String location);
}