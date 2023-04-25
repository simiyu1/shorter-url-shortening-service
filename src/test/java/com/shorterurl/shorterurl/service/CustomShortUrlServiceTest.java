package com.shorterurl.shorterurl.service;

import com.shorterurl.shorterurl.model.UrlMapping;
import com.shorterurl.shorterurl.repository.UrlMappingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

public class CustomShortUrlServiceTest {

    @InjectMocks
    private CustomShortUrlService customShortUrlService;

    @Mock
    private UrlMappingRepository urlMappingRepository;

    @Mock
    private UrlShorteningService urlShorteningService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testReserveCustomShortUrl_aliasAlreadyReserved() {
        String longUrl = "http://example.com";
        String customAlias = "customAlias";
        String ipAddress = "172.0.0.1";
        String userId = "2b0803c1f5787acd7e4724";

        UrlMapping existingUrlMapping = new UrlMapping(longUrl, customAlias);
        when(urlMappingRepository.findByShortUrl(customAlias)).thenReturn(existingUrlMapping);

        UrlMapping result = customShortUrlService.reserveCustomShortUrl(longUrl, customAlias, ipAddress, userId);

        assertNull(result);
        verify(urlMappingRepository, times(1)).findByShortUrl(customAlias);
        verify(urlMappingRepository, never()).save(any(UrlMapping.class));
    }

    @Test
    public void testReserveCustomShortUrl_newAlias() {
        String longUrl = "http://example.com";
        String customAlias = "customAlias";
        String ipAddress = "172.0.0.1";
        String userId = "2b0803c1f5787acd7e4724";

        when(urlMappingRepository.findByShortUrl(customAlias)).thenReturn(null);
        when(urlMappingRepository.save(any(UrlMapping.class))).thenAnswer(invocation -> {
            UrlMapping urlMapping = invocation.getArgument(0);
            urlMapping.setShortUrl(customAlias);
            return urlMapping;
        });

        UrlMapping result = customShortUrlService.reserveCustomShortUrl(longUrl, customAlias, ipAddress, userId);

        assertNotNull(result);
        assertEquals(longUrl, result.getLongUrl());
        assertEquals(customAlias, result.getShortUrl());
        verify(urlMappingRepository, times(1)).findByShortUrl(customAlias);
        verify(urlMappingRepository, times(1)).save(any(UrlMapping.class));
    }
}
