package com.mounika.careerconnect.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mounika.careerconnect.dto.JobDTO;
import com.mounika.careerconnect.entity.Job;
import com.mounika.careerconnect.exception.JobNotFoundException;
import com.mounika.careerconnect.repository.JobRepository;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    public JobDTO saveJob(JobDTO jobDTO) {

        Job job = new Job();

        job.setJobTitle(jobDTO.getJobTitle());
        job.setCompanyName(jobDTO.getCompanyName());
        job.setLocation(jobDTO.getLocation());
        job.setSalary(jobDTO.getSalary());
        job.setDescription(jobDTO.getDescription());

        Job savedJob = jobRepository.save(job);

        return convertToDTO(savedJob);
    }
    public List<JobDTO> getAllJobs() {
        return jobRepository.findAll()
                .stream()
                .map(job -> convertToDTO(job))
                .toList();
    }
    private JobDTO convertToDTO(Job job) {

        JobDTO jobDTO = new JobDTO();

        jobDTO.setJobId(job.getJobId());
        jobDTO.setJobTitle(job.getJobTitle());
        jobDTO.setCompanyName(job.getCompanyName());
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
    public JobDTO updateJob(JobDTO jobDTO) {

        Job job = jobRepository.findById(jobDTO.getJobId())
                .orElseThrow(() ->
                    new JobNotFoundException(
                        "Job not found with ID: " + jobDTO.getJobId()
                    )
                );

        job.setJobTitle(jobDTO.getJobTitle());
        job.setCompanyName(jobDTO.getCompanyName());
        job.setLocation(jobDTO.getLocation());
        job.setSalary(jobDTO.getSalary());
        job.setDescription(jobDTO.getDescription());

        Job updatedJob = jobRepository.save(job);

        return convertToDTO(updatedJob);
    }
    public void deleteJob(int id) {

        Job job = jobRepository.findById(id)
                .orElseThrow(() ->
                    new JobNotFoundException(
                        "Job not found with ID: " + id
                    )
                );

        jobRepository.delete(job);
    }
}
