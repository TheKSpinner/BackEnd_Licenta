package org.example.backend.rezultate;

import org.example.backend.consultatii.Consultatii;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RezultateRepository extends CrudRepository<Rezultate, Long> {

    @Query("SELECT r FROM Rezultate r WHERE r.pacienti.id_pacient = :pacientId")
    List<Rezultate> findByPacientId(Long pacientId);

}