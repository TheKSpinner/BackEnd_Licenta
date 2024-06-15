package org.example.backend.medici;

import org.example.backend.pacienti.Pacienti;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface MediciRepository extends CrudRepository<Medici, Long> {
    @Query("SELECT p FROM Medici p where p.Email=:email")
    Optional<Medici> findByEmail(String email);

}