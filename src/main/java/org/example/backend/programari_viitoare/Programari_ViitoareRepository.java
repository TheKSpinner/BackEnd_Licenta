package org.example.backend.programari_viitoare;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;


public interface Programari_ViitoareRepository extends CrudRepository<Programari_Viitoare, Long> {
    @Query("SELECT DISTINCT p FROM Programari_Viitoare p WHERE p.medici.id_medic = :medicId")
    List<Programari_Viitoare> findByMedici_IdMedic(@Param("medicId") Long medicId);
    @Query("SELECT DISTINCT p FROM Programari_Viitoare p WHERE p.pacienti.id_pacient = :pacientId")
    List<Programari_Viitoare> findByPacient_IdPacient(@Param("pacientId") Long pacientId);
    @Query("SELECT p FROM Programari_Viitoare p WHERE p.Date_Ora < :dateTime")
    List<Programari_Viitoare> findAllByDate_OraAfter(LocalDateTime dateTime);

    @Query("SELECT p FROM Programari_Viitoare p WHERE p.Date_Ora >= :startDateTime AND p.Date_Ora <= :endDateTime")
    List<Programari_Viitoare> findAllBetweenDates(LocalDateTime startDateTime, LocalDateTime endDateTime);

}