package com.nestfinder.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "pg_owners")
public class PGOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;
    private String phone;
}
