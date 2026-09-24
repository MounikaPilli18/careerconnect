package com.mounika.careerconnect.controller;

import com.mounika.careerconnect.dto.ApplicationDTO;
import com.mounika.careerconnect.dto.ApplicantDTO;
import com.mounika.careerconnect.entity.Application;
import com.mounika.careerconnect.service.ApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(
            ApplicationService applicationService) {

        this.applicationService = applicationService;
    }

    @PostMapping("/apply/{jobId}")
    public ResponseEntity<?> applyForJob(
            @PathVariable int jobId,
            Authentication authentication) {

        try {
            Application application =
                    applicationService.applyForJob(
                            jobId,
                            authentication.getName()
                    );

            ApplicationDTO response = new ApplicationDTO(
                    application.getApplicationId(),
                    application.getJob().getJobId(),
                    application.getJob().getJobTitle(),
                    application.getJob().getCompanyName(),
                    application.getJob().getLocation(),
                    application.getJob().getSalary(),
                    application.getAppliedAt(),
                    application.getStatus()
            );

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @GetMapping("/my")
    public ResponseEntity<List<ApplicationDTO>> getMyApplications(
            Authentication authentication) {

        return ResponseEntity.ok(
                applicationService.getApplicationsByStudent(
                        authentication.getName()
                )
        );
    }

    @GetMapping("/job/{jobId}")
    public ResponseEntity<?> getApplicantsForJob(
            @PathVariable int jobId,
            Authentication authentication) {

        try {

            List<ApplicantDTO> applicants =
                    applicationService.getApplicantsForJob(
                            jobId,
                            authentication.getName()
                    );

            return ResponseEntity.ok(applicants);

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }
    @PutMapping("/{applicationId}/status")
    public ResponseEntity<?> updateApplicationStatus(
            @PathVariable Long applicationId,
            @RequestParam String status,
            Authentication authentication) {

        try {

            Application application =
                    applicationService.updateApplicationStatus(
                            applicationId,
                            status,
                            authentication.getName()
                    );

            ApplicationDTO response = new ApplicationDTO(
                    application.getApplicationId(),
                    application.getJob().getJobId(),
                    application.getJob().getJobTitle(),
                    application.getJob().getCompanyName(),
                    application.getJob().getLocation(),
                    application.getJob().getSalary(),
                    application.getAppliedAt(),
                    application.getStatus()
            );

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

}
