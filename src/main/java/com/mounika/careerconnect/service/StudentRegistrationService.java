package com.mounika.careerconnect.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mounika.careerconnect.entity.Student;
import com.mounika.careerconnect.entity.User;
import com.mounika.careerconnect.repository.StudentRepository;
import com.mounika.careerconnect.repository.UserRepository;


@Service
public class StudentRegistrationService {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;

    public StudentRegistrationService(
            UserRepository userRepository,
            StudentRepository studentRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Student registerStudent(
            String username,
            String password,
            String name,
            String phone,
            String email,
            String resume,
            String location,
            Double cgpa) {

        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        if (studentRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User(
                username,
                passwordEncoder.encode(password),
                "STUDENT"
        );

        user = userRepository.save(user);

        Student student = new Student(
                user,
                name,
                phone,
                email,
                resume,
                location,
                cgpa
        );

        return studentRepository.save(student);
    }
public Student getStudentByUsername(String username) {

    User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));

    return studentRepository.findByUserId(user.getId())
            .orElseThrow(() -> new RuntimeException("Student profile not found"));
}
}