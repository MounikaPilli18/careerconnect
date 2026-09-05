package com.mounika.careerconnect.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mounika.careerconnect.dto.JobDTO;
import com.mounika.careerconnect.dto.PageResponse;
import com.mounika.careerconnect.entity.Company;
import com.mounika.careerconnect.entity.Job;
import com.mounika.careerconnect.entity.User;
import com.mounika.careerconnect.exception.InvalidSortFieldException;
import com.mounika.careerconnect.exception.JobNotFoundException;
import com.mounika.careerconnect.repository.CompanyRepository;
import com.mounika.careerconnect.repository.JobRepository;
import com.mounika.careerconnect.repository.UserRepository;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyRepository companyRepository;

    public JobDTO saveJob(JobDTO jobDTO, String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("User not found: " + username));

        if (!"COMPANY".equalsIgnoreCase(user.getRole())) {
            throw new RuntimeException(
                    "Only recruiters/companies can create jobs"
            );
        }

        Company company = companyRepository.findByUserId(user.getId())
                .orElseThrow(() ->
                        new RuntimeException("Company profile not found"));

        Job job = new Job();

        job.setJobTitle(jobDTO.getJobTitle());
        job.setCompanyName(company.getCompanyName());
        job.setLocation(jobDTO.getLocation());
        job.setSalary(jobDTO.getSalary());
        job.setDescription(jobDTO.getDescription());

        // Connect job with the logged-in company
        job.setCompany(company);

        Job savedJob = jobRepository.save(job);

        return convertToDTO(savedJob);
    }

    public List<JobDTO> getAllJobs() {
        return jobRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    public PageResponse<JobDTO> getJobsWithPagination(
            int page, int size, String field, String direction) {

        if (page < 0) {
            throw new IllegalArgumentException(
                    "Page number must be 0 or greater"
            );
        }

        if (size <= 0) {
            throw new IllegalArgumentException(
                    "Page size must be greater than 0"
            );
        }

        Sort sort = createSort(field, direction);

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Job> jobPage = jobRepository.findAll(pageable);

        List<JobDTO> jobs = jobPage.getContent()
                .stream()
                .map(this::convertToDTO)
                .toList();

        return new PageResponse<>(
                jobs,
                jobPage.getNumber(),
                jobPage.getSize(),
                jobPage.getTotalElements(),
                jobPage.getTotalPages(),
                jobPage.isLast()
        );
    }

    public List<JobDTO> getJobsWithSorting(
            String field, String direction) {

        Sort sort = createSort(field, direction);

        List<Job> jobs = jobRepository.findAll(sort);

        return jobs.stream()
                .map(this::convertToDTO)
                .toList();
    }

    public List<JobDTO> getJobsByTitle(String jobTitle) {

        List<Job> jobs = jobRepository.findByJobTitle(jobTitle);

        if (jobs.isEmpty()) {
            throw new JobNotFoundException(
                    "No jobs found with title: " + jobTitle
            );
        }

        return jobs.stream()
                .map(this::convertToDTO)
                .toList();
    }

    public List<JobDTO> searchJobs(String keyword) {

        List<Job> jobs = jobRepository.searchJobs(keyword);

        if (jobs.isEmpty()) {
            throw new JobNotFoundException(
                    "No jobs found for keyword: " + keyword
            );
        }

        return jobs.stream()
                .map(this::convertToDTO)
                .toList();
    }

    public List<JobDTO> getJobsByCompanyName(String companyName) {

        List<Job> jobs = jobRepository.findByCompanyName(companyName);

        if (jobs.isEmpty()) {
            throw new JobNotFoundException(
                    "No jobs found for company: " + companyName
            );
        }

        return jobs.stream()
                .map(this::convertToDTO)
                .toList();
    }

    public List<JobDTO> getJobsByLocation(String location) {

        List<Job> jobs = jobRepository.findByLocation(location);

        if (jobs.isEmpty()) {
            throw new JobNotFoundException(
                    "No jobs found in location: " + location
            );
        }

        return jobs.stream()
                .map(this::convertToDTO)
                .toList();
    }

    public List<JobDTO> getJobsBySalary(double salary) {

        List<Job> jobs =
                jobRepository.findBySalaryGreaterThanEqual(salary);

        if (jobs.isEmpty()) {
            throw new JobNotFoundException(
                    "No jobs found with salary greater than or equal to "
                            + salary
            );
        }

        return jobs.stream()
                .map(this::convertToDTO)
                .toList();
    }

    private Sort createSort(String field, String direction) {

        if (!field.equals("jobTitle") &&
            !field.equals("companyName") &&
            !field.equals("location") &&
            !field.equals("salary")) {

            throw new InvalidSortFieldException(
                    "Invalid sort field. Allowed fields are: "
                    + "jobTitle, companyName, location, salary"
            );
        }

        if (!direction.equalsIgnoreCase("asc") &&
            !direction.equalsIgnoreCase("desc")) {

            throw new IllegalArgumentException(
                    "Invalid sort direction. "
                    + "Allowed values are: asc, desc"
            );
        }

        return direction.equalsIgnoreCase("desc")
                ? Sort.by(field).descending()
                : Sort.by(field).ascending();
    }

    private JobDTO convertToDTO(Job job) {

        JobDTO jobDTO = new JobDTO();

        jobDTO.setJobId(job.getJobId());
        jobDTO.setJobTitle(job.getJobTitle());

        // Get company name from the Company relationship
        if (job.getCompany() != null) {
            jobDTO.setCompanyName(
                    job.getCompany().getCompanyName()
            );
        } else {
            jobDTO.setCompanyName(job.getCompanyName());
        }

        jobDTO.setLocation(job.getLocation());
        jobDTO.setSalary(job.getSalary());
        jobDTO.setDescription(job.getDescription());

        return jobDTO;
    }

    public JobDTO getJobById(int id) {

        Job job = jobRepository.findById(id)
                .orElseThrow(() ->
                        new JobNotFoundException(
                                "Job not found with ID: " + id
                        )
                );

        return convertToDTO(job);
    }

    public JobDTO updateJob(
            JobDTO jobDTO,
            String username) {

        Job job = jobRepository.findById(jobDTO.getJobId())
                .orElseThrow(() ->
                        new JobNotFoundException(
                                "Job not found with ID: "
                                        + jobDTO.getJobId()
                        )
                );

        verifyJobOwner(job, username);

        job.setJobTitle(jobDTO.getJobTitle());
        job.setLocation(jobDTO.getLocation());
        job.setSalary(jobDTO.getSalary());
        job.setDescription(jobDTO.getDescription());

        // Company name should come from the company relationship
        job.setCompanyName(
                job.getCompany().getCompanyName()
        );

        Job updatedJob = jobRepository.save(job);

        return convertToDTO(updatedJob);
    }

    public void deleteJob(
            int id,
            String username) {

        Job job = jobRepository.findById(id)
                .orElseThrow(() ->
                        new JobNotFoundException(
                                "Job not found with ID: " + id
                        )
                );

        verifyJobOwner(job, username);

        jobRepository.delete(job);
    }

    private void verifyJobOwner(
            Job job,
            String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found: " + username
                        )
                );

        if (job.getCompany() == null ||
            job.getCompany().getUser() == null ||
            !job.getCompany().getUser().getId()
                    .equals(user.getId())) {

            throw new RuntimeException(
                    "You are not authorized to modify this job"
            );
        }
    }
}