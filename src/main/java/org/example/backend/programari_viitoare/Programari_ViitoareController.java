package org.example.backend.programari_viitoare;

import org.example.backend.pacienti.Pacienti;
import org.example.backend.pacienti.PacientiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/programari_viitoare")
@CrossOrigin(origins = "http://localhost:3000")
public class Programari_ViitoareController {
    @Autowired
    private Programari_ViitoareRepository programariViitoareRepository;

    @Autowired
    private PacientiRepository pacientiRepository;

    @Autowired
    private GoogleMeetService googleMeetService;

    @DeleteMapping("/delete/{Id_Programari}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Void> deleteProgramareViitoare(@PathVariable Long Id_Programari) {
        try {
            programariViitoareRepository.deleteById(Id_Programari);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/upload")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Programari_Viitoare> createProgramareViitoare(@RequestBody Programari_Viitoare programareViitoare) {
        try {
            // Fetch and attach the Pacienti entity to the appointment
            Pacienti pacienti = pacientiRepository.findById(programareViitoare.getPacienti().getId_Pacient())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid Pacienti ID"));
            System.out.println(programareViitoare.getPacienti().getId_Pacient());

            programareViitoare.setPacienti(pacienti);

            if (programareViitoare.getOnline() == 1) {
                String googleMeetLink = googleMeetService.createGoogleMeetLink(programareViitoare);
                programareViitoare.setGooglemeetlink(googleMeetLink);
            }
            Programari_Viitoare savedProgramare = programariViitoareRepository.save(programareViitoare);
            return new ResponseEntity<>(savedProgramare, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
