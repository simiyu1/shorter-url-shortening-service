package com.shorterurl.shorterurl.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.shorterurl.shorterurl.model.Click;

@Repository
public interface ClickRepository extends MongoRepository<Click, String> {
    List<Click> findByUrlMappingId(String urlMappingId);
}
