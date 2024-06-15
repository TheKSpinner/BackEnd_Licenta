package org.example.backend.programari_viitoare;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@EnableScheduling
public class ScheduledDelete {

    @Autowired
    private Programari_ViitoareRepository programariViitoareRepository;

    public ScheduledDelete() {
        System.out.println("SchedulerService initialized!");
    }


    // Runs every hour
    @Scheduled(fixedRate = 3600000)
    public void deleteOldProgramariViitoare() {
        System.out.println("Running scheduled task.");
        LocalDateTime twelveHoursAgo = LocalDateTime.now().minusHours(12);
        List<Programari_Viitoare> outdatedProgramari = programariViitoareRepository.findAllByDate_OraAfter(twelveHoursAgo);
        programariViitoareRepository.deleteAll(outdatedProgramari);
    }
}
