package com.mounika.careerconnect.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mounika.careerconnect.entity.Job;

public interface JobRepository extends JpaRepository<Job, Integer> {

    List<Job> findByJobTitle(String jobTitle);

    List<Job> findBySalaryGreaterThanEqual(double salary);

    List<Job> findByCompanyName(String companyName);

    List<Job> findByLocation(String location);

    @Query("""
        SELECT j FROM Job j
        WHERE LOWER(j.jobTitle) LIKE LOWER(CONCAT('%', :keyword, '%'))
           OR LOWER(j.companyName) LIKE LOWER(CONCAT('%', :keyword, '%'))
           OR LOWER(j.location) LIKE LOWER(CONCAT('%', :keyword, '%'))
           OR LOWER(j.description) LIKE LOWER(CONCAT('%', :keyword, '%'))
    """)
    List<Job> searchJobs(@Param("keyword") String keyword);
}