package com.shorterurl.shorterurl.service;

import com.shorterurl.shorterurl.model.UrlMapping;
import com.shorterurl.shorterurl.repository.UrlMappingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class UrlShorteningServiceTest {

    @InjectMocks
    private UrlShorteningService urlShorteningService;

    @Mock
    private UrlMappingRepository urlMappingRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(urlShorteningService, "shortUrlDomain", "http://api.ntoya.link/");
    }

    @Test
    public void testCreateShortUrl_existingUrlMapping() {
        String longUrl = "http://longurlexample.com";
        String shortUrl = "http://api.ntoya.link/abcdef";

        UrlMapping existingUrlMapping = new UrlMapping(longUrl, shortUrl);
        when(urlMappingRepository.findByLongUrl(longUrl)).thenReturn(existingUrlMapping);

        UrlMapping result = urlShorteningService.createShortUrl(longUrl);

        assertEquals(existingUrlMapping, result);
        verify(urlMappingRepository, times(1)).findByLongUrl(longUrl);
        verify(urlMappingRepository, never()).save(any(UrlMapping.class));
    }

    @Test
    public void testCreateShortUrl_newUrlMapping() {
        String longUrl = "http://longurlexample.com";
        String shortUrl = "http://api.ntoya.link/abcdef";

        when(urlMappingRepository.findByLongUrl(longUrl)).thenReturn(null);
        when(urlMappingRepository.save(any(UrlMapping.class))).thenAnswer(invocation -> {
            UrlMapping urlMapping = invocation.getArgument(0);
            urlMapping.setShortUrl(shortUrl);
            return urlMapping;
        });

        UrlMapping result = urlShorteningService.createShortUrl(longUrl);

        assertNotNull(result);
        assertEquals(longUrl, result.getLongUrl());
        assertEquals(shortUrl, result.getShortUrl());
        verify(urlMappingRepository, times(1)).findByLongUrl(longUrl);
        verify(urlMappingRepository, times(1)).save(any(UrlMapping.class));
    }

    @Test
    public void testGetLongUrl() {
        String longUrl = "http://longurlexample.com";
        String shortUrl = "http://api.ntoya.link/abcdef";

        UrlMapping urlMapping = new UrlMapping(longUrl, shortUrl);
        when(urlMappingRepository.findByShortUrl(shortUrl)).thenReturn(urlMapping);

        UrlMapping result = urlShorteningService.getLongUrl(shortUrl);

        assertEquals(urlMapping, result);
        verify(urlMappingRepository, times(1)).findByShortUrl(shortUrl);
    }
}
    // @Test
    // public void testGetAllUrlMappings() {
    //     List<UrlMapping> urlMappings = List.of(
    //             new UrlMapping("http://longurlexample.com/1", "http://api.ntoya.link/abc123"),
    //             new UrlMapping("http://longurlexample.com/2", "http://api.ntoya.link/def456")
    //     );

    //     when(urlMappingRepository.findAll()).thenReturn(urlMappings);

    //     List<UrlMapping> result = urlShorteningService.getAllUrlMappings();

       

