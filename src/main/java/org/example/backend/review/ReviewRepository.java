package org.example.backend.review;

import org.example.backend.programari_viitoare.Programari_Viitoare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("SELECT DISTINCT p FROM Review p WHERE p.medic.id_medic = :id_medic")
    List<Review> findByMedicId(Long id_medic);

    @Query("SELECT DISTINCT p FROM Review p WHERE p.id_consultatie = :id_consultatie")
    Optional<Review> findByIdConsultatie(Long id_consultatie);
}
