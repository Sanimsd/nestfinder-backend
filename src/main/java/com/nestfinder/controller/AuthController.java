package com.nestfinder.controller;

import com.nestfinder.dto.DTOs.*;
import com.nestfinder.model.PGOwner;
import com.nestfinder.model.Student;
import com.nestfinder.repository.PGOwnerRepository;
import com.nestfinder.repository.StudentRepository;
import com.nestfinder.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired private StudentRepository studentRepository;
    @Autowired private PGOwnerRepository ownerRepository;
    @Autowired private JwtUtil jwtUtil;
    @Autowired private PasswordEncoder passwordEncoder;

    @PostMapping("/register/student")
    public ResponseEntity<?> registerStudent(@RequestBody StudentRegisterRequest req) {
        if (studentRepository.findByEmail(req.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Email already exists"));
        }
        Student student = new Student();
        student.setName(req.getName());
        student.setEmail(req.getEmail());
        student.setPassword(passwordEncoder.encode(req.getPassword()));
        student.setPhone(req.getPhone());
        student.setCollege(req.getCollege());
        studentRepository.save(student);
        return ResponseEntity.ok(Map.of("message", "Student registered successfully"));
    }

    @PostMapping("/register/owner")
    public ResponseEntity<?> registerOwner(@RequestBody OwnerRegisterRequest req) {
        if (ownerRepository.findByEmail(req.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Email already exists"));
        }
        PGOwner owner = new PGOwner();
        owner.setName(req.getName());
        owner.setEmail(req.getEmail());
        owner.setPassword(passwordEncoder.encode(req.getPassword()));
        owner.setPhone(req.getPhone());
        ownerRepository.save(owner);
        return ResponseEntity.ok(Map.of("message", "Owner registered successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        if ("STUDENT".equalsIgnoreCase(req.getRole())) {
            return studentRepository.findByEmail(req.getEmail())
                .filter(s -> passwordEncoder.matches(req.getPassword(), s.getPassword()))
                .map(s -> {
                    String token = jwtUtil.generateToken(s.getEmail(), "STUDENT");
                    return ResponseEntity.ok(new LoginResponse(token, "STUDENT", s.getName(), s.getId()));
                })
                .orElse(ResponseEntity.status(401).build());
        } else {
            return ownerRepository.findByEmail(req.getEmail())
                .filter(o -> passwordEncoder.matches(req.getPassword(), o.getPassword()))
                .map(o -> {
                    String token = jwtUtil.generateToken(o.getEmail(), "OWNER");
                    return ResponseEntity.ok(new LoginResponse(token, "OWNER", o.getName(), o.getId()));
                })
                .orElse(ResponseEntity.status(401).build());
        }
    }
}
