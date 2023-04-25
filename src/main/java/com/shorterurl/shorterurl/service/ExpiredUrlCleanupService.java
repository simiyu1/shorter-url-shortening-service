package com.shorterurl.shorterurl.service;

import com.shorterurl.shorterurl.model.UrlMapping;
import com.shorterurl.shorterurl.repository.UrlMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ExpiredUrlCleanupService {

    @Autowired
    private UrlMappingRepository urlMappingRepository;

    public void deleteExpiredUrls() {
        LocalDateTime now = LocalDateTime.now();
        urlMappingRepository.deleteByExpirationBefore(now);
    }
}
