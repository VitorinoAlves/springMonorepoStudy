package org.example.services;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class CacheCustomManager {

    @CacheEvict(value = "memeDoDia", allEntries = true)
    @Scheduled(cron = "0 0 0 * * *")
    public void clearMemeDoDiaCache() {
        System.out.println("Limpando cache do Meme do Dia...");
    }
}
