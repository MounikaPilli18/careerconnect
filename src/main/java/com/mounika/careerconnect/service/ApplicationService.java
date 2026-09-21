package com.mounika.careerconnect.service;

import com.mounika.careerconnect.dto.ApplicationDTO;
import com.mounika.careerconnect.dto.ApplicantDTO;
import com.mounika.careerconnect.entity.Application;
import com.mounika.careerconnect.entity.Company;
import com.mounika.careerconnect.entity.Job;
import com.mounika.careerconnect.entity.Student;
import com.mounika.careerconnect.entity.User;
import com.mounika.careerconnect.repository.ApplicationRepository;
import com.mounika.careerconnect.repository.CompanyRepository;
import com.mounika.careerconnect.repository.JobRepository;
import com.mounika.careerconnect.repository.StudentRepository;
import com.mounika.careerconnect.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final StudentRepository studentRepository;
    private final JobRepository jobRepository;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;

    public ApplicationService(
            ApplicationRepository applicationRepository,
            StudentRepository studentRepository,
            JobRepository jobRepository,
            UserRepository userRepository,
            CompanyRepository companyRepository) {

        this.applicationRepository = applicationRepository;
        this.studentRepository = studentRepository;
        this.jobRepository = jobRepository;
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
    }

    @Transactional
    public Application applyForJob(int jobId, String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        if (!"STUDENT".equalsIgnoreCase(user.getRole())) {
            throw new RuntimeException(
                    "Only students can apply for jobs"
            );
        }

        Student student = studentRepository.findByUserId(user.getId())
                .orElseThrow(() ->
                        new RuntimeException("Student profile not found"));

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() ->
                        new RuntimeException("Job not found"));

        if (applicationRepository
                .findByStudentIdAndJobJobId(
                        student.getId(),
                        jobId
                )
                .isPresent()) {

            throw new RuntimeException(
                    "You have already applied for this job"
            );
        }

        Application application = new Application(
                student,
                job,
                LocalDateTime.now(),
                "APPLIED"
        );

        return applicationRepository.save(application);
    }

    public List<ApplicationDTO> getApplicationsByStudent(
            String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Student student = studentRepository.findByUserId(user.getId())
                .orElseThrow(() ->
                        new RuntimeException("Student profile not found"));

        return applicationRepository
                .findByStudentId(student.getId())
                .stream()
                .map(this::convertToApplicationDTO)
                .toList();
    }

    public List<ApplicantDTO> getApplicantsForJob(
            int jobId,
            String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        if (!"COMPANY".equalsIgnoreCase(user.getRole())) {
            throw new RuntimeException(
                    "Only companies can view applicants"
            );
        }

        Company company = companyRepository.findByUserId(user.getId())
                .orElseThrow(() ->
                        new RuntimeException("Company profile not found"));

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() ->
                        new RuntimeException("Job not found"));

        if (job.getCompany() == null ||
            job.getCompany().getId() == null ||
            !job.getCompany().getId().equals(company.getId())) {

            throw new RuntimeException(
                    "You are not authorized to view applicants for this job"
            );
        }

        return applicationRepository
                .findByJobJobId(jobId)
                .stream()
                .map(this::convertToApplicantDTO)
                .toList();
    }

    private ApplicationDTO convertToApplicationDTO(
            Application application) {

        Job job = application.getJob();

        return new ApplicationDTO(
                application.getApplicationId(),
                job.getJobId(),
                job.getJobTitle(),
                job.getCompanyName(),
                job.getLocation(),
                job.getSalary(),
                application.getAppliedAt(),
                application.getStatus()
        );
    }

    private ApplicantDTO convertToApplicantDTO(
            Application application) {

        Student student = application.getStudent();

        return new ApplicantDTO(
                application.getApplicationId(),
                student.getName(),
                student.getEmail(),
                student.getPhone(),
                student.getLocation(),
                student.getCgpa(),
                student.getResume(),
                application.getStatus()
        );
    }
}
