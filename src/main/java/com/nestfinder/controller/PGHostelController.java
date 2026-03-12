package com.nestfinder.controller;

import com.nestfinder.dto.DTOs.*;
import com.nestfinder.model.PGHostel;
import com.nestfinder.model.PGOwner;
import com.nestfinder.repository.PGHostelRepository;
import com.nestfinder.repository.PGOwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pg")
@CrossOrigin(origins = "*")
public class PGHostelController {

    @Autowired private PGHostelRepository pgRepository;
    @Autowired private PGOwnerRepository ownerRepository;

    @PostMapping
    public ResponseEntity<?> createPG(@RequestBody PGHostelRequest req) {
        PGOwner owner = ownerRepository.findById(req.getOwnerId())
            .orElseThrow(() -> new RuntimeException("Owner not found"));
        PGHostel pg = new PGHostel();
        pg.setName(req.getName());
        pg.setAddress(req.getAddress());
        pg.setCity(req.getCity());
        pg.setLocality(req.getLocality());
        pg.setPrice(req.getPrice());
        pg.setType(req.getType());
        pg.setGender(req.getGender());
        pg.setAmenities(req.getAmenities());
        pg.setDescription(req.getDescription());
        pg.setTotalRooms(req.getTotalRooms());
        pg.setAvailableRooms(req.getAvailableRooms());
        pg.setOwner(owner);
        pgRepository.save(pg);
        return ResponseEntity.ok(Map.of("message", "PG created successfully", "id", pg.getId()));
    }

    @GetMapping("/search")
    public ResponseEntity<List<PGHostel>> search(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) Double maxPrice) {
        List<PGHostel> results;
        if (city != null && gender != null) {
            results = pgRepository.findByCityIgnoreCaseAndGenderIgnoreCase(city, gender);
        } else if (city != null) {
            results = pgRepository.findByCityIgnoreCase(city);
        } else if (maxPrice != null) {
            results = pgRepository.findByPriceLessThanEqual(maxPrice);
        } else {
            results = pgRepository.findAll();
        }
        return ResponseEntity.ok(results);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPG(@PathVariable Long id) {
        return pgRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}
