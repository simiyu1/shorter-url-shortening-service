package com.shorterurl.shorterurl.service;

import com.shorterurl.shorterurl.model.UrlMapping;
import com.shorterurl.shorterurl.repository.UrlMappingRepository;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomShortUrlService {
    @Autowired
    private UrlMappingRepository urlMappingRepository;

    @Autowired
    private UrlShorteningService urlShorteningService;

    public UrlMapping reserveCustomShortUrl(String longUrl, String customAlias, String ipAddress, String userId) {
        // Check if custom alias is already reserved
        UrlMapping existingUrlMapping = urlMappingRepository.findByShortUrl(customAlias);
        if (existingUrlMapping != null) {
            return null; // Custom alias already exists
        }

        // Create a new UrlMapping with the custom short URL
        UrlMapping urlMapping = new UrlMapping(longUrl, customAlias);
        urlMapping.setIpAddress(ipAddress);
        urlMapping.setUserId(userId);
        LocalDateTime expiration = LocalDateTime.now().plusHours(48);
        urlMapping.setExpiration(expiration);
        return urlMappingRepository.save(urlMapping);
    }
}