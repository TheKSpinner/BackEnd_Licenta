package org.example.backend.tokenRequest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:3000")
public class TokenRequestController {

    @Autowired
    private PasswordResetEmailSender passwordResetEmailSender;

    @PostMapping("/requestToken")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> requestResetToken(@RequestBody Map<String, String> requestBody) {
        String email = requestBody.get("email");
        String token = requestBody.get("token"); // Assuming the token is generated and passed from the front-end

        passwordResetEmailSender.sendResetTokenEmail(email, token);
        return ResponseEntity.ok().body("Reset token sent to " + email);
    }
}
