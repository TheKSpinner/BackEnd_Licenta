package org.example.backend.mesagerie;

import org.example.backend.medici.Medici;
import org.example.backend.medici.MediciRepository;
import org.example.backend.programari_viitoare.SchedulerSMS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/")
@CrossOrigin(origins = "http://localhost:3000")
public class EmailRequestController {

    @Autowired
    private MediciRepository medicRepository;

    @Autowired
    private SchedulerSMS emailsender;

    @PostMapping("/medici/send-email")
    public ResponseEntity<Void> sendEmail(@RequestBody EmailRequest emailRequest) {
        try {

            Medici recipientMedic = medicRepository.findById(emailRequest.getMedicId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid medic ID"));

            String recipientEmail = recipientMedic.getEmail();
            String subject = emailRequest.getSubject();
            String body = "You have a new message from " + recipientMedic.getNume() + " " + recipientMedic.getPrenume() + "\n"+  emailRequest.getBody();

            emailsender.sendEmailNotification(recipientEmail, subject, body);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
