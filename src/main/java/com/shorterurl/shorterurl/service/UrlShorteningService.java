package com.shorterurl.shorterurl.service;

import com.shorterurl.shorterurl.model.UrlMapping;
import com.shorterurl.shorterurl.repository.UrlMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UrlShorteningService {

    @Autowired
    private UrlMappingRepository urlMappingRepository;

    @Value("${app.short-url-domain}")
    private String shortUrlDomain;


    public UrlMapping createShortUrl(String longUrl, String ipAddress, String userId) {
        // Check if the long URL already exists in the repository
        UrlMapping existingUrlMapping = urlMappingRepository.findByLongUrl(longUrl);
        if (existingUrlMapping != null) {
            return existingUrlMapping;
        }

        // Generate the short URL
        String shortUrl = generateShortUrl(longUrl);

        // Create a new UrlMapping with the short URL
        UrlMapping urlMapping = new UrlMapping(longUrl, shortUrl);
        urlMapping.setIpAddress(ipAddress);
        urlMapping.setUserId(userId);
        LocalDateTime expiration = LocalDateTime.now().plusHours(48);
        urlMapping.setExpiration(expiration);
        return urlMappingRepository.save(urlMapping);
    }

    public UrlMapping getLongUrl(String shortUrl) {
        return urlMappingRepository.findByShortUrl(shortUrl);
    }

    private String generateShortUrl(String longUrl) {
        String uniqueId = getUniqueId(longUrl);
        return uniqueId;
    }

    private String getUniqueId(String longUrl) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(longUrl.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString().substring(0, 6); // Using the first 6 characters for the unique identifier
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Unable to generate unique identifier", e);
        }
    }

    public List<UrlMapping> getAllUrlMappings() {
        return urlMappingRepository.findAll();
    }

    public UrlMapping saveUrlMapping(UrlMapping urlMapping) {
        return urlMappingRepository.save(urlMapping);
    }
}
