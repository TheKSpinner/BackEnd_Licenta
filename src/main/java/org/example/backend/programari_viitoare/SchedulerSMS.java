package org.example.backend.programari_viitoare;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;

@Service
@EnableScheduling
public class SchedulerSMS {

    @Autowired
    private JavaMailSender emailSender;

    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @Value("${twilio.trial.number}")
    private String trialNumber;


    @Autowired
    private Programari_ViitoareRepository programariViitoareRepository;

    private static final String ACCOUNT_SID = "YOUR_TWILIO_SID";
    private static final String AUTH_TOKEN = "YOUR_TWILIO_AUTH_TOKEN";

    @Scheduled(fixedRate = 3600000*12)  // every hour
    public void sendPreAppointmentNotifications() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime twelveHoursAhead = now.plusHours(12);
        List<Programari_Viitoare> upcomingAppointments = programariViitoareRepository.findAllBetweenDates(now, twelveHoursAhead);

        upcomingAppointments.forEach(appointment -> {
            System.out.println(appointment.getPacienti().getNume() + " " + appointment.getPacienti().getPrenume());
            System.out.println("Trying to send sms to:" + appointment.getPacienti().getTelefon());
            sendSMSNotification(appointment.getPacienti().getTelefon(), "Reminder: You have an appointment in 12 hours.");
            System.out.println("Trying to send email to:" + appointment.getPacienti().getEmail());
            sendEmailNotification(appointment.getPacienti().getEmail(), "Appointment Reminder", "You have an appointment in 12 hours.");
        });
    }

    private void sendSMSNotification(String phoneNumber, String message) {
        Twilio.init(accountSid, authToken);
        Message.creator(
                new PhoneNumber(phoneNumber),
                new PhoneNumber(trialNumber),
                message
        ).create();
    }

    public void sendEmailNotification(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }
}