package com.mounika.careerconnect.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import com.mounika.careerconnect.dto.StudentProfileDTO;
import com.mounika.careerconnect.entity.Student;
import com.mounika.careerconnect.service.StudentRegistrationService;

@RestController
@RequestMapping("/students")
public class StudentRegistrationController {

    private final StudentRegistrationService studentRegistrationService;

    public StudentRegistrationController(
            StudentRegistrationService studentRegistrationService) {
        this.studentRegistrationService = studentRegistrationService;
    }

    @PostMapping("/register")
    public ResponseEntity<Student> registerStudent(
            @RequestBody StudentRegistrationRequest request) {

        Student student = studentRegistrationService.registerStudent(
                request.getUsername(),
                request.getPassword(),
                request.getName(),
                request.getPhone(),
                request.getEmail(),
                request.getResume(),
                request.getLocation(),
                request.getCgpa()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(student);
    }
    @GetMapping("/me")
    public ResponseEntity<StudentProfileDTO> getMyProfile(Authentication authentication) {

        String username = authentication.getName();

        Student student =
                studentRegistrationService.getStudentByUsername(username);

StudentProfileDTO profile = new StudentProfileDTO(
            student.getId(),
            student.getUser().getUsername(),
            student.getUser().getRole(),
            student.getName(),
            student.getPhone(),
            student.getEmail(),
            student.getResume(),
            student.getLocation(),
            student.getCgpa()
    );

return ResponseEntity.ok(profile);
    }

    public static class StudentRegistrationRequest {

        private String username;
        private String password;
        private String name;
        private String phone;
        private String email;
        private String resume;
        private String location;
        private Double cgpa;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getResume() {
            return resume;
        }

        public void setResume(String resume) {
            this.resume = resume;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public Double getCgpa() {
            return cgpa;
        }

        public void setCgpa(Double cgpa) {
            this.cgpa = cgpa;
        }
    }
}