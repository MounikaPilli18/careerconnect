package com.mounika.careerconnect.controller;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mounika.careerconnect.dto.JobDTO;
import com.mounika.careerconnect.entity.Job;
import com.mounika.careerconnect.service.JobService;

import jakarta.validation.Valid;

@RestController
public class JobController {

    @Autowired
    private JobService jobService;

    @PostMapping("/jobs")
    public JobDTO saveJob(@Valid @RequestBody JobDTO jobDTO) {
        return jobService.saveJob(jobDTO);
    }
    @GetMapping("/jobs")
    public List<JobDTO> getAllJobs() {
        return jobService.getAllJobs();
    }
    @GetMapping("/jobs/{id}")
    public JobDTO getJobById(@PathVariable int id) {
        return jobService.getJobById(id);
    }
    @PutMapping("/jobs/{id}")
    public JobDTO updateJob(@PathVariable int id,
                            @Valid @RequestBody JobDTO jobDTO) {

        jobDTO.setJobId(id);

        return jobService.updateJob(jobDTO);
    }
    @DeleteMapping("/jobs/{id}")
    public String deleteJob(@PathVariable int id) {
        jobService.deleteJob(id);
        return "Job deleted successfully!";
    }
}