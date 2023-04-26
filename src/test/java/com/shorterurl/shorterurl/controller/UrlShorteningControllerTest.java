package com.shorterurl.shorterurl.controller;

import com.shorterurl.shorterurl.model.UrlMapping;
import com.shorterurl.shorterurl.service.AuthenticationService;
import com.shorterurl.shorterurl.service.ClickService;
import com.shorterurl.shorterurl.service.CustomShortUrlService;
import com.shorterurl.shorterurl.service.UrlShorteningService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.mockito.Mockito.mock;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UrlShorteningControllerTest {

    @InjectMocks
    private UrlShorteningController urlShorteningController;

    @Mock
    private UrlShorteningService urlShorteningService;

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private CustomShortUrlService customShortUrlService;

    @Mock
    private ClickService clickService;

    @Test
    public void createShortUrl_whenNoCustomAlias_properUrlMappingCreated() {
        UrlMapping expectedUrlMapping = new UrlMapping();
        // when(urlShorteningService.createShortUrl(any(), any(), any())).thenReturn(expectedUrlMapping);
    
        // Map<String, String> requestBody = new HashMap<>();
        // requestBody.put("longUrl", "longUrl");
        // requestBody.put("ipAddress", "0.0.0.0");
    
        // // Create a mock HttpServletRequest object and set the header value
        // HttpServletRequest request = mock(HttpServletRequest.class);
        // when(request.getHeader("X-Forwarded-For")).thenReturn("0.0.0.0");
    
        // // Pass the mock request object to the createShortUrl method
        // ResponseEntity<UrlMapping> responseEntity = urlShorteningController.createShortUrl(requestBody, request);
    
        // assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        // assertEquals(expectedUrlMapping, responseEntity.getBody());
    }
}