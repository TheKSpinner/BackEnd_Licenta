package org.example.backend.tokenRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class PasswordResetEmailSender {

    @Autowired
    private JavaMailSender emailSender;

    public void sendResetTokenEmail(String to, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("theknightspinner@gmail.com"); // Optionally set or use the default from application.properties
        message.setTo(to);
        message.setSubject("Password Reset Request");
        message.setText("Here is your password reset token: " + token + "\nPlease use this token to reset your password.");
        emailSender.send(message);
    }
}
