package org.example.backend.review;

import org.example.backend.medici.Medici;
import org.example.backend.medici.MediciRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MediciRepository mediciRepository;

    public Review addReview(Long medicId, Review review) {
        Medici medic = mediciRepository.findById(medicId).orElseThrow(() -> new RuntimeException("Medic not found"));
        review.setMedic(medic);
        Review savedReview = reviewRepository.save(review);

        // Recalculate the average rating and update the medic
        Long newAverageRating = (long)calculateAverageRating(medicId);
        medic.setRating(newAverageRating);
        mediciRepository.save(medic);

        return savedReview;
    }

    public boolean hasReviewForConsultation(Long id_consultatie) {
        return reviewRepository.findByIdConsultatie(id_consultatie).isPresent();
    }

    public List<Review> getReviewsForMedic(Long medicId) {
        return reviewRepository.findByMedicId(medicId);
    }

    public double calculateAverageRating(Long medicId) {
        List<Review> reviews = reviewRepository.findByMedicId(medicId);
        return reviews.stream().mapToDouble(Review::getRating).average().orElse(0);
    }
}
