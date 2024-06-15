package org.example.backend.medici;

import org.example.backend.consultatii.Consultatii;
import org.example.backend.consultatii.ConsultatiiRepository;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.web.multipart.MultipartFile;
import org.example.backend.programari_viitoare.Programari_Viitoare;
import org.example.backend.programari_viitoare.Programari_ViitoareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.backend.security.AESUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/medici")
@CrossOrigin(origins = "http://localhost:3000")
public class MediciController {

    @Autowired
    private MediciRepository mediciRepository;

    @Autowired
    private ConsultatiiRepository consultatiiRepository;

    @Autowired
    private Programari_ViitoareRepository programariViitoareRepository;

    @PostMapping("/{id}/update/profil")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> updateMedicProfile(
            @PathVariable Long id,
            @RequestParam("Nume") String Nume,
            @RequestParam("domeniul_Medic") String domeniul_Medic,
            @RequestParam(value = "Fotografie_Profil", required = false) MultipartFile Fotografie_Profil,
            @RequestParam(value = "titlu", required = false) String titlu) {

        Optional<Medici> mediciOptional = mediciRepository.findById(id);
        if (mediciOptional.isPresent()) {
            Medici medici = mediciOptional.get();
            String[] nume_prenume = Nume.split("\\s");
            if(nume_prenume.length>1) {
                String nume = nume_prenume[0];
                String prenume = nume_prenume[1];
                if (prenume.length() > 1) {
                    medici.setPrenume(prenume);
                }
                if (nume.length() > 1) {
                    medici.setNume(nume);
                }
            }
            if (!Objects.equals(domeniul_Medic, "")) {
                medici.setDomeniul_Medic(domeniul_Medic);
            }
            if (!Objects.equals(titlu, "")) {
                medici.setTitlu(titlu);
            }
            if (Fotografie_Profil != null && !Fotografie_Profil.isEmpty()) {
                try {
                    // Original image size
                    byte[] originalImageBytes = Fotografie_Profil.getBytes();
                    System.out.println("Original image size: " + originalImageBytes.length + " bytes");

                    // Compress the image
                    ByteArrayOutputStream compressedImageStream = new ByteArrayOutputStream();
                    Thumbnails.of(Fotografie_Profil.getInputStream())
                            .size(275, 275) // Resize to 75x75
                            .outputFormat("jpg") // Ensure output is in JPG format
                            .outputQuality(0.85) // Set the quality to 75%
                            .toOutputStream(compressedImageStream);

                    byte[] compressedImageBytes = compressedImageStream.toByteArray();
                    System.out.println("Compressed image size: " + compressedImageBytes.length + " bytes");

                    String base64Image = Base64.getEncoder().encodeToString(compressedImageBytes);
                    medici.setFotografie_Profil("data:image/jpeg;base64,"+base64Image);

                } catch (IOException e) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error compressing image");
                }
            }

            Medici updatedMedic = mediciRepository.save(medici);
            return ResponseEntity.ok(updatedMedic);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/check-email")
    public ResponseEntity<?> checkMedicEmail(@RequestBody Map<String, String> emailMap) {
        String email = emailMap.get("email");
        Optional<Medici> mediciOptional = mediciRepository.findByEmail(email);
        if (mediciOptional.isPresent()) {
            Medici medici = mediciOptional.get();
            return ResponseEntity.ok().body(Map.of("exists", true, "userId", medici.getId_medic(), "type", "medici"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("exists", false));
        }
    }

    @PutMapping("/update_password/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> updateMedicPassword(@PathVariable Long id, @RequestBody Map<String, String> passwordData) {
        Optional<Medici> mediciOptional = mediciRepository.findById(id);
        if (mediciOptional.isPresent()) {
            Medici medici = mediciOptional.get();
            try {
                medici.setPassword(AESUtil.encrypt(passwordData.get("password"))); // Encrypt password
                mediciRepository.save(medici);
                return ResponseEntity.ok().body("Password updated successfully.");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/render")
    public ResponseEntity<List<Map<String, Object>>> getAllMediciRender() {
        Iterable<Medici> mediciList = mediciRepository.findAll();
        List<Map<String, Object>> response = new ArrayList<>();

        for (Medici medici : mediciList) {
            try {
                medici.setPassword(AESUtil.decrypt(medici.getPassword()));
            } catch (Exception e) {
                // Handle decryption error
            }
            Map<String, Object> medicData = new HashMap<>();
            medicData.put("id_medic",medici.getId_medic());
            medicData.put("titlu", medici.getTitlu());
            medicData.put("nume", medici.getNume());
            medicData.put("prenume", medici.getPrenume());
            medicData.put("domeniul_Medic", medici.getDomeniul_Medic());
            medicData.put("fotografie_Profil", medici.getFotografie_Profil());
            medicData.put("locatie", medici.getLocatie());
            medicData.put("rating", medici.getRating());
            response.add(medicData);
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/log-in")
    public Iterable<Medici> getAllMedici() {
        Iterable<Medici> mediciList = mediciRepository.findAll();
        mediciList.forEach(medici -> {
            try {
                medici.setPassword(AESUtil.decrypt(medici.getPassword()));
            } catch (Exception e) {
                // Handle decryption error
            }
        });
        return mediciList;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medici> getMedicById(@PathVariable Long id) {
        Optional<Medici> mediciOptional = mediciRepository.findById(id);
        if (mediciOptional.isPresent()) {
            Medici medici = mediciOptional.get();
            try {
                medici.setPassword(AESUtil.decrypt(medici.getPassword()));
            } catch (Exception e) {
                // Handle decryption error
            }
            return ResponseEntity.ok(medici);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/")
    public ResponseEntity<Medici> createMedic(@RequestBody Medici medic) {
        try {
            medic.setPassword(AESUtil.encrypt(medic.getPassword()));
            Medici savedMedic = mediciRepository.save(medic);
            savedMedic.setPassword(AESUtil.decrypt(savedMedic.getPassword()));
            return new ResponseEntity<>(savedMedic, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Medici> updateMedic(@PathVariable Long id, @RequestBody Medici updatedMedic) {
        Optional<Medici> mediciOptional = mediciRepository.findById(id);
        if (mediciOptional.isPresent()) {
            try {
                updatedMedic.setPassword(AESUtil.encrypt(updatedMedic.getPassword()));
                updatedMedic.setId_medic(id);
                Medici savedMedic = mediciRepository.save(updatedMedic);
                savedMedic.setPassword(AESUtil.decrypt(savedMedic.getPassword()));
                return ResponseEntity.ok(savedMedic);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedic(@PathVariable Long id) {
        Optional<Medici> mediciOptional = mediciRepository.findById(id);
        if (mediciOptional.isPresent()) {
            mediciRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{medicId}/programari_viitoare")
    public ResponseEntity<List<Programari_Viitoare>> getProgramariViitoareByMedicId(@PathVariable Long medicId) {
        List<Programari_Viitoare> programariViitoareList = programariViitoareRepository.findByMedici_IdMedic(medicId);
        if (!programariViitoareList.isEmpty()) {
            return ResponseEntity.ok(programariViitoareList);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{medicId}/consultatii")
    public ResponseEntity<List<Consultatii>> getConsultationsByMedicId(@PathVariable Long medicId) {
        List<Consultatii> consultatiiList = consultatiiRepository.findByMedici_IdMedic(medicId);
        if (!consultatiiList.isEmpty()) {
            return ResponseEntity.ok(consultatiiList);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
