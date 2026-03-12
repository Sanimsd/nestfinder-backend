package com.nestfinder.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "pg_hostels")
public class PGHostel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;
    private String city;
    private String locality;
    private Double price;
    private String type;
    private String gender;

    @Column(columnDefinition = "TEXT")
    private String amenities;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Integer totalRooms;
    private Integer availableRooms;
    private Double rating = 0.0;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private PGOwner owner;
}
