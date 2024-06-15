package org.example.backend.servicii;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/servicii")
@CrossOrigin(origins = "http://localhost:3000")
public class ServiciiController {

    @Autowired
    private ServiciiRepository serviciiRepository;

    // Get all servicii
    @GetMapping("/")
    public ResponseEntity<List<Servicii>> getAllServicii() {
        List<Servicii> serviciiList = (List<Servicii>) serviciiRepository.findAll();
        return ResponseEntity.ok(serviciiList);
    }

    // Get servicii by ID
    @GetMapping("/{id}")
    public ResponseEntity<Servicii> getServiciiById(@PathVariable("id") Long id) {
        Optional<Servicii> serviciiOptional = serviciiRepository.findById(id);
        if (serviciiOptional.isPresent()) {
            return ResponseEntity.ok(serviciiOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Create a new serviciu
    @PostMapping
    public ResponseEntity<Servicii> createServiciu(@RequestBody Servicii servicii) {
        Servicii createdServiciu = serviciiRepository.save(servicii);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdServiciu);
    }

    // Update an existing serviciu
    @PutMapping("/{id}")
    public ResponseEntity<Servicii> updateServiciu(@PathVariable("id") Long id, @RequestBody Servicii updatedServiciu) {
        if (serviciiRepository.existsById(id)) {
            updatedServiciu.setId_Servicii(id);
            Servicii savedServiciu = serviciiRepository.save(updatedServiciu);
            return ResponseEntity.ok(savedServiciu);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a serviciu
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServiciu(@PathVariable("id") Long id) {
        if (serviciiRepository.existsById(id)) {
            serviciiRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
