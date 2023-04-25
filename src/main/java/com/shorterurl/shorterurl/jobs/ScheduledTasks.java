package com.shorterurl.shorterurl.jobs;


import com.shorterurl.shorterurl.service.ExpiredUrlCleanupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    @Autowired
    private ExpiredUrlCleanupService expiredUrlCleanupService;

    @Scheduled(fixedRateString = "${app.cleanup.interval}")
    public void deleteExpiredUrls() {
        expiredUrlCleanupService.deleteExpiredUrls();
    }
}
