package com.nestfinder.repository;
import com.nestfinder.model.PGHostel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface PGHostelRepository extends JpaRepository<PGHostel, Long> {
    List<PGHostel> findByCityIgnoreCase(String city);
    List<PGHostel> findByCityIgnoreCaseAndGenderIgnoreCase(String city, String gender);
    List<PGHostel> findByPriceLessThanEqual(Double price);
}
