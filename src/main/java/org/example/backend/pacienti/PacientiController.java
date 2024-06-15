package org.example.backend.pacienti;

import ch.qos.logback.classic.Logger;
import org.example.backend.consultatii.Consultatii;
import org.example.backend.consultatii.ConsultatiiController;
import org.example.backend.consultatii.ConsultatiiRepository;
import org.example.backend.programari_viitoare.Programari_Viitoare;
import org.example.backend.rezultate.Rezultate;
import org.example.backend.rezultate.RezultateRepository;
import org.example.backend.programari_viitoare.Programari_ViitoareRepository;
import org.example.backend.security.AESUtil;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/pacienti")
@CrossOrigin(origins = "http://localhost:3000")
public class PacientiController {

    private static final Logger log = (Logger) LoggerFactory.getLogger(ConsultatiiController.class);

    @Autowired
    private PacientiRepository pacientiRepository;

    @Autowired
    private ConsultatiiRepository consultatiiRepository;

    @Autowired
    private RezultateRepository rezultateRepository;

    @Autowired
    private Programari_ViitoareRepository programariViitoareRepository;

    @GetMapping("/")
    public Iterable<Pacienti> getAllPacienti() {
        Iterable<Pacienti> pacientiList = pacientiRepository.findAll();
        pacientiList.forEach(pacienti -> {
            try {
                pacienti.setPassword(AESUtil.decrypt(pacienti.getPassword()));
            } catch (Exception e) {
                log.error("Error decrypting password", e);
            }
        });
        return pacientiList;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pacienti> getPacientById(@PathVariable Long id) {
        Optional<Pacienti> pacientiOptional = pacientiRepository.findById(id);
        if (pacientiOptional.isPresent()) {
            Pacienti pacienti = pacientiOptional.get();
            try {
                pacienti.setPassword(AESUtil.decrypt(pacienti.getPassword()));
            } catch (Exception e) {
                log.error("Error decrypting password", e);
            }
            return ResponseEntity.ok(pacienti);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/upload")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Pacienti> createPacientViaUpload(@RequestBody Pacienti pacienti) {
        try {
            pacienti.setPassword(AESUtil.encrypt(pacienti.getPassword()));
            Pacienti savedPacient = pacientiRepository.save(pacienti);
            savedPacient.setPassword(AESUtil.decrypt(savedPacient.getPassword()));
            return new ResponseEntity<>(savedPacient, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error encrypting password", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/")
    public ResponseEntity<Pacienti> createPacient(@RequestBody Pacienti pacienti) {
        try {
            pacienti.setPassword(AESUtil.encrypt(pacienti.getPassword()));
            pacienti.setId_Pacient((long) 150);
            Pacienti savedPacient = pacientiRepository.save(pacienti);
            savedPacient.setPassword(AESUtil.decrypt(savedPacient.getPassword()));
            return new ResponseEntity<>(savedPacient, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error encrypting password", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/update_password/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> updatePacientPassword(@PathVariable Long id, @RequestBody Map<String, String> passwordData) {
        Optional<Pacienti> pacientiOptional = pacientiRepository.findById(id);
        if (pacientiOptional.isPresent()) {
            Pacienti pacienti = pacientiOptional.get();
            try {
                pacienti.setPassword(AESUtil.encrypt(passwordData.get("password"))); // Encrypt password
                pacientiRepository.save(pacienti);
                return ResponseEntity.ok().body("Password updated successfully.");
            } catch (Exception e) {
                log.error("Error encrypting password", e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/check-email")
    public ResponseEntity<?> checkPacientEmail(@RequestBody Map<String, String> emailMap) {
        String email = emailMap.get("email");
        Optional<Pacienti> pacientiOptional = pacientiRepository.findByEmail(email);
        if (pacientiOptional.isPresent()) {
            Pacienti pacienti = pacientiOptional.get();
            try {
                pacienti.setPassword(AESUtil.decrypt(pacienti.getPassword()));
            } catch (Exception e) {
                log.error("Error decrypting password", e);
            }
            return ResponseEntity.ok().body(Map.of("exists", true, "userId", pacienti.getId_Pacient(), "type", "pacienti"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("exists", false));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePacient(@PathVariable Long id) {
        Optional<Pacienti> pacientiOptional = pacientiRepository.findById(id);
        if (pacientiOptional.isPresent()) {
            pacientiRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Get consultations by pacient ID
    @GetMapping("/{pacientId}/consultatii")
    public ResponseEntity<List<Consultatii>> getConsultationsByPacientId(@PathVariable Long pacientId) {
        List<Consultatii> consultatiiList = consultatiiRepository.findByPacientId(pacientId);
        if (!consultatiiList.isEmpty()) {
            return ResponseEntity.ok(consultatiiList);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Get results by pacient ID
    @GetMapping("/{pacientId}/rezultate")
    public List<Rezultate> getResultsByPacientId(@PathVariable Long pacientId) {
        return rezultateRepository.findByPacientId(pacientId);
    }

    // Get upcoming appointments by pacient ID
    @GetMapping("/{pacientId}/programari_viitoare")
    public List<Programari_Viitoare> getUpcomingAppointmentsByPacientId(@PathVariable Long pacientId) {
        return programariViitoareRepository.findByPacient_IdPacient(pacientId);
    }
}

