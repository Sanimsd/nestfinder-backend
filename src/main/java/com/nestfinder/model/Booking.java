package com.nestfinder.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "pg_id")
    private PGHostel pgHostel;

    private String checkInDate;
    private Integer months;
    private Double totalAmount;
    private String status = "PENDING";
    private String bookingReference;
    private LocalDateTime createdAt = LocalDateTime.now();
}
