package com.mounika.careerconnect.repository;

import com.mounika.careerconnect.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface ApplicationRepository
        extends JpaRepository<Application, Long> {

    Optional<Application> findByStudentIdAndJobJobId(
            Long studentId,
            int jobId
    );

    List<Application> findByStudentId(Long studentId);

    List<Application> findByJobJobId(int jobId);
}