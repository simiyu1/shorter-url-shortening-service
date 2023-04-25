package com.shorterurl.shorterurl.repository;


import com.shorterurl.shorterurl.model.UrlMapping;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlMappingRepository extends MongoRepository<UrlMapping, String> {
    UrlMapping findByShortUrl(String shortUrl);
    UrlMapping findByLongUrl(String longUrl);
    void deleteByExpirationBefore(LocalDateTime dateTime);
}