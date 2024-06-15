package org.example.backend.consultatii;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ConsultatiiRepository extends CrudRepository<Consultatii, Long> {


    @Query("SELECT DISTINCT c FROM Consultatii c JOIN FETCH c.pacienti JOIN FETCH c.medici WHERE c.pacienti.id_pacient = :pacientId")
    List<Consultatii> findByPacientId(@Param("pacientId") Long pacientId);

    @Query("SELECT DISTINCT c FROM Consultatii c JOIN FETCH c.pacienti JOIN FETCH c.medici WHERE c.medici.id_medic = :medicId")
    List<Consultatii> findByMedici_IdMedic(@Param("medicId") Long medicId);

}