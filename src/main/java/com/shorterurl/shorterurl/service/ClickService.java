package com.shorterurl.shorterurl.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shorterurl.shorterurl.model.Click;
import com.shorterurl.shorterurl.repository.ClickRepository;

@Service
public class ClickService {
    @Autowired
    private ClickRepository clickRepository;

    public Click saveClick(Click click) {
        return clickRepository.save(click);
    }

    public List<Click> getClicksByUrlMappingId(String urlMappingId) {
        return clickRepository.findByUrlMappingId(urlMappingId);
    }
} 
