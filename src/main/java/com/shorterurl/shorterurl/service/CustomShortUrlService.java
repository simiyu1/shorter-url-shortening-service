package com.shorterurl.shorterurl.service;

import com.shorterurl.shorterurl.model.UrlMapping;
import com.shorterurl.shorterurl.repository.UrlMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomShortUrlService {
    @Autowired
    private UrlMappingRepository urlMappingRepository;

    @Autowired
    private UrlShorteningService urlShorteningService;

    public UrlMapping reserveCustomShortUrl(String longUrl, String customAlias) {
        // Check if custom alias is already reserved
        UrlMapping existingUrlMapping = urlMappingRepository.findByShortUrl(customAlias);
        if (existingUrlMapping != null) {
            return null; // Custom alias already exists
        }

        // Create a new UrlMapping with the custom short URL
        UrlMapping urlMapping = new UrlMapping(longUrl, customAlias);
        return urlMappingRepository.save(urlMapping);
    }
}