package org.example.backend.review;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
@CrossOrigin(origins = "http://localhost:3000")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @PostMapping("/{medicId}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Review> addReview(@PathVariable Long medicId, @RequestBody Review review) {
        Review savedReview = reviewService.addReview(medicId, review);
        return ResponseEntity.ok(savedReview);
    }

    @GetMapping("/{medicId}")
    public ResponseEntity<List<Review>> getReviewsForMedic(@PathVariable Long medicId) {
        List<Review> reviews = reviewService.getReviewsForMedic(medicId);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/{medicId}/average-rating")
    public ResponseEntity<Double> getAverageRating(@PathVariable Long medicId) {
        double averageRating = reviewService.calculateAverageRating(medicId);
        return ResponseEntity.ok(averageRating);
    }

    @GetMapping("/hasReview/{id_consultatie}")
    public ResponseEntity<Boolean> hasReviewForConsultation(@PathVariable Long id_consultatie) {
        boolean hasReview = reviewService.hasReviewForConsultation(id_consultatie);
        return ResponseEntity.ok(hasReview);
    }
}

