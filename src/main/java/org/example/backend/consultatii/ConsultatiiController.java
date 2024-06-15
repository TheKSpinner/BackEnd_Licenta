package org.example.backend.consultatii;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
import java.util.Base64;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "http://localhost:3000")
public class ConsultatiiController {

    @Autowired
    private ConsultatiiRepository consultatiiRepository;

    private static final Logger log = (Logger) LoggerFactory.getLogger(ConsultatiiController.class);

    @GetMapping("/consultatii/{id}/diagnosticPDF")
    public ResponseEntity<InputStreamResource> downloadDiagnosticPDF(@PathVariable Long id) {
        Consultatii consultatii = consultatiiRepository.findById(id).orElse(null);
        if (consultatii != null && consultatii.getDiagnosticPDF() != null) {
            byte[] data = Base64.getDecoder().decode(consultatii.getDiagnosticPDF());
            ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(new InputStreamResource(inputStream));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/consultatii/{id}/prescriptionPDF")
    public ResponseEntity<InputStreamResource> downloadPrescriptionPDF(@PathVariable Long id) {
        Consultatii consultatii = consultatiiRepository.findById(id).orElse(null);
        if (consultatii != null && consultatii.getPrescriptiePDF() != null) {
            byte[] data = Base64.getDecoder().decode(consultatii.getPrescriptiePDF());
            ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(new InputStreamResource(inputStream));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/consultatii/upload")
    public ResponseEntity<?> addConsultation(@RequestBody Consultatii consultation) {
        Logger log = (Logger) LoggerFactory.getLogger(Consultatii.class);

        // Log the lengths of the received Base64 strings for diagnostic and prescriptive PDFs
        log.info("Received diagnostic PDF Base64 length: {}", consultation.getDiagnosticPDF() != null ? consultation.getDiagnosticPDF().length() : "NULL");
        log.info("Received prescription PDF Base64 length: {}", consultation.getPrescriptiePDF() != null ? consultation.getPrescriptiePDF().length() : "NULL");

        // Directly save the consultation data without decoding
        try {
            Consultatii savedConsultation = consultatiiRepository.save(consultation);
            return ResponseEntity.ok(savedConsultation);
        } catch (Exception e) {
            log.error("Failed to save consultation: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save consultation: " + e.getMessage());
        }
    }

}