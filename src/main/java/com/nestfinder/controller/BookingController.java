package com.nestfinder.controller;

import com.nestfinder.dto.DTOs.*;
import com.nestfinder.model.Booking;
import com.nestfinder.model.PGHostel;
import com.nestfinder.model.Student;
import com.nestfinder.repository.BookingRepository;
import com.nestfinder.repository.PGHostelRepository;
import com.nestfinder.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "*")
public class BookingController {

    @Autowired private BookingRepository bookingRepository;
    @Autowired private StudentRepository studentRepository;
    @Autowired private PGHostelRepository pgRepository;

    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody BookingRequest req) {
        Student student = studentRepository.findById(req.getStudentId())
            .orElseThrow(() -> new RuntimeException("Student not found"));
        PGHostel pg = pgRepository.findById(req.getPgId())
            .orElseThrow(() -> new RuntimeException("PG not found"));

        Booking booking = new Booking();
        booking.setStudent(student);
        booking.setPgHostel(pg);
        booking.setCheckInDate(req.getCheckInDate());
        booking.setMonths(req.getMonths());
        booking.setTotalAmount(pg.getPrice() * req.getMonths());
        booking.setBookingReference("NF" + System.currentTimeMillis());
        bookingRepository.save(booking);

        return ResponseEntity.ok(Map.of(
            "message", "Booking created successfully",
            "bookingId", booking.getId(),
            "reference", booking.getBookingReference(),
            "totalAmount", booking.getTotalAmount()
        ));
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Booking>> getStudentBookings(@PathVariable Long studentId) {
        return ResponseEntity.ok(bookingRepository.findByStudentId(studentId));
    }
}
