package com.mounika.careerconnect.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mounika.careerconnect.entity.Job;

public interface JobRepository extends JpaRepository<Job, Integer> {

}