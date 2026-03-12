package com.nestfinder.repository;
import com.nestfinder.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByPgHostelId(Long pgId);
}
