package org.example.backend.pacienti;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface PacientiRepository extends CrudRepository<Pacienti, Long> {
    @Query("SELECT p FROM Pacienti p where p.Email=:email")
    Optional<Pacienti> findByEmail(String email);
}
