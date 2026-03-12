package com.nestfinder.controller;

import com.nestfinder.dto.DTOs.*;
import com.nestfinder.model.PGHostel;
import com.nestfinder.model.Review;
import com.nestfinder.model.Student;
import com.nestfinder.repository.PGHostelRepository;
import com.nestfinder.repository.ReviewRepository;
import com.nestfinder.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = "*")
public class ReviewController {

    @Autowired private ReviewRepository reviewRepository;
    @Autowired private StudentRepository studentRepository;
    @Autowired private PGHostelRepository pgRepository;

    @PostMapping
    public ResponseEntity<?> addReview(@RequestBody ReviewRequest req) {
        Student student = studentRepository.findById(req.getStudentId())
            .orElseThrow(() -> new RuntimeException("Student not found"));
        PGHostel pg = pgRepository.findById(req.getPgId())
            .orElseThrow(() -> new RuntimeException("PG not found"));

        Review review = new Review();
        review.setStudent(student);
        review.setPgHostel(pg);
        review.setRating(req.getRating());
        review.setComment(req.getComment());
        reviewRepository.save(review);

        // Update PG average rating
        List<Review> reviews = reviewRepository.findByPgHostelId(pg.getId());
        double avg = reviews.stream().mapToInt(Review::getRating).average().orElse(0);
        pg.setRating(avg);
        pgRepository.save(pg);

        return ResponseEntity.ok(Map.of("message", "Review added successfully"));
    }

    @GetMapping("/pg/{pgId}")
    public ResponseEntity<List<Review>> getPGReviews(@PathVariable Long pgId) {
        return ResponseEntity.ok(reviewRepository.findByPgHostelId(pgId));
    }
}
