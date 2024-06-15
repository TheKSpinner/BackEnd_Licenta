package org.example.backend.programari_viitoare;

import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.*;
import com.google.api.client.util.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.format.DateTimeFormatter;

@Service
public class GoogleMeetService {

    @Autowired
    private GoogleCalendarService googleCalendarService;

    public String createGoogleMeetLink(Programari_Viitoare programareViitoare) throws GeneralSecurityException, IOException {
        Calendar service = googleCalendarService.getCalendarService();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        String formattedDateTime = programareViitoare.getDate_Ora().format(formatter);

        Event event = new Event()
                .setSummary("Appointment with " + programareViitoare.getMedici().getNume())
                .setDescription("Online Appointment")
                .setStart(new EventDateTime().setDateTime(new DateTime(formattedDateTime)).setTimeZone("UTC"))
                .setEnd(new EventDateTime().setDateTime(new DateTime(formattedDateTime)).setTimeZone("UTC"));

        ConferenceData conferenceData = new ConferenceData()
                .setCreateRequest(new CreateConferenceRequest()
                        .setRequestId("sample123")
                        .setConferenceSolutionKey(new ConferenceSolutionKey()
                                .setType("hangoutsMeet"))
                );

        event.setConferenceData(conferenceData);

        String calendarId = "primary";
        event = service.events().insert(calendarId, event)
                .setConferenceDataVersion(1)
                .execute();

        if (event.getConferenceData() != null && event.getConferenceData().getEntryPoints() != null) {
            for (EntryPoint entryPoint : event.getConferenceData().getEntryPoints()) {
                if ("video".equals(entryPoint.getEntryPointType())) {
                    return entryPoint.getUri();
                }
            }
        }
        return null;
    }
}
