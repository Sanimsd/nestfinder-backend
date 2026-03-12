package com.nestfinder.repository;
import com.nestfinder.model.PGOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface PGOwnerRepository extends JpaRepository<PGOwner, Long> {
    Optional<PGOwner> findByEmail(String email);
}
