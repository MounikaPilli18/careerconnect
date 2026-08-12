package com.mounika.careerconnect.controller;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mounika.careerconnect.dto.JobDTO;
import com.mounika.careerconnect.dto.PageResponse;
import com.mounika.careerconnect.response.ApiResponse;
import com.mounika.careerconnect.service.JobService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
public class JobController {
	
    @Autowired
    private JobService jobService;
    
    @Operation(
            summary = "Create a new job",
            description = "Creates a new job in CareerConnect."
    )
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "201",
            description = "Job created successfully"
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "400",
            description = "Invalid job data"
        )
    })
    @PostMapping("/jobs")
    public ApiResponse<JobDTO> saveJob(@Valid @RequestBody JobDTO jobDTO) {

        JobDTO savedJob = jobService.saveJob(jobDTO);

        return new ApiResponse<>(
                "Job created successfully.",
                201,
                savedJob
        );
    }
    @Operation(
            summary = "Get all jobs",
            description = "Fetches all available jobs from CareerConnect."
    )
    @GetMapping("/jobs")
    public ApiResponse<List<JobDTO>> getAllJobs() {

        return new ApiResponse<>(
                "Job list fetched successfully.",
                200,
                jobService.getAllJobs()
        );
    }
    @Operation(
            summary = "Get job by ID",
            description = "Fetches a job using its unique job ID."
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Job fetched successfully"
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "404",
            description = "Job not found"
        )
    })

    @GetMapping("/jobs/{id}")
    public ApiResponse<JobDTO> getJobById(@PathVariable int id) {

        return new ApiResponse<>(
                "Job fetched successfully.",
                200,
                jobService.getJobById(id)
        );
    }
    @Operation(
            summary = "Search jobs by title",
            description = "Fetches jobs matching the specified job title."
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Jobs fetched successfully"
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "404",
            description = "No jobs found with the specified title"
        )
    })
    @GetMapping("/jobs/title/{jobTitle}")
    public ApiResponse<List<JobDTO>> getJobsByTitle(@PathVariable String jobTitle) {

        return new ApiResponse<>(
                "Jobs fetched successfully.",
                200,
                jobService.getJobsByTitle(jobTitle)
        );
    }
    @Operation(
    		summary = "Search jobs by company",
    		description = "Fetches jobs matching the specified job company name"
    		)
    @io.swagger.v3.oas.annotations.responses.ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Jobs fetched successfully"
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "404",
            description = "No jobs found with the specified company"
        )
    })
    @GetMapping("/jobs/company/{companyName}")
    public ApiResponse<List<JobDTO>> getJobsByCompanyName(
            @PathVariable String companyName) {

        return new ApiResponse<>(
                "Jobs fetched successfully.",
                200,
                jobService.getJobsByCompanyName(companyName)
        );
    }
    @Operation(
    		summary = "Search jobs by location",
    		description = "Fetches jobs matching the specified job location"
    		)
    @io.swagger.v3.oas.annotations.responses.ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Jobs fetched successfully"
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "404",
            description = "No jobs found with the specified location"
        )
    })
    @GetMapping("/jobs/location/{location}")
    public ApiResponse<List<JobDTO>> getJobsByLocation(
            @PathVariable String location) {

        return new ApiResponse<>(
                "Jobs fetched successfully.",
                200,
                jobService.getJobsByLocation(location)
        );
    }
    @Operation(
            summary = "Search jobs by minimum salary",
            description = "Fetches jobs with a salary greater than or equal to the specified amount."
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Jobs fetched successfully"
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "404",
            description = "No jobs found with the specified salary"
        )
    })
    @GetMapping("/jobs/salary/{salary}")
    public ApiResponse<List<JobDTO>> getJobsBySalary(@PathVariable double salary) {

        return new ApiResponse<>(
                "Jobs fetched successfully.",
                200,
                jobService.getJobsBySalary(salary)
        );
    }
    @Operation(
            summary = "Get paginated and sorted jobs",
            description = "Fetches jobs using pagination and sorting."
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Jobs fetched successfully"
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "400",
            description = "Invalid pagination or sort parameters"
        )
    })
    @GetMapping("/jobs/page")
    public ApiResponse<PageResponse<JobDTO>> getJobsWithPagination(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String field,
            @RequestParam String direction) {

        return new ApiResponse<>(
                "Jobs fetched successfully.",
                200,
                jobService.getJobsWithPagination(page, size, field, direction)
        );
    }
    @Operation(
            summary = "Sort jobs",
            description = "Fetches jobs sorted by a specified field in ascending or descending order."
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Jobs sorted successfully"
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "400",
            description = "Invalid sort field or sort direction"
        )
    })
    @GetMapping("/jobs/sort")
    public ApiResponse<List<JobDTO>> getJobsWithSorting(
            @RequestParam String field,
            @RequestParam String direction) {

        return new ApiResponse<>(
                "Jobs fetched successfully.",
                200,
                jobService.getJobsWithSorting(field, direction)
        );
    }
    @Operation(
            summary = "Update a job",
            description = "Updates an existing job using its unique job ID."
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Job updated successfully"
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "400",
            description = "Invalid job data"
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "404",
            description = "Job not found"
        )
    })
    
    @PutMapping("/jobs/{id}")
    public ApiResponse<JobDTO> updateJob(
            @PathVariable int id,
            @Valid @RequestBody JobDTO jobDTO) {

        jobDTO.setJobId(id);

        return new ApiResponse<>(
                "Job updated successfully.",
                200,
                jobService.updateJob(jobDTO)
        );
    }
    @Operation(
            summary = "Delete a job",
            description = "Deletes an existing job using its unique job ID."
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Job deleted successfully"
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "404",
            description = "Job not found"
        )
    })
    @DeleteMapping("/jobs/{id}")
    public ApiResponse<String> deleteJob(@PathVariable int id) {

        jobService.deleteJob(id);

        return new ApiResponse<>(
                "Job deleted successfully.",
                200,
                null
        );
    }
}