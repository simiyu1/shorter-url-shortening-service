package com.shorterurl.shorterurl.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.shorterurl.shorterurl.model.UrlMapping;
import com.shorterurl.shorterurl.service.AuthenticationService;
import com.shorterurl.shorterurl.service.CustomShortUrlService;
import com.shorterurl.shorterurl.service.UrlShorteningService;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;

@ExtendWith(MockitoExtension.class)
public class UrlShorteningControllerTest {

    @Mock
    private UrlShorteningService urlShorteningService;

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private CustomShortUrlService customShortUrlService;

    private UrlShorteningController urlShorteningController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        urlShorteningController = new UrlShorteningController();
        urlShorteningController.setUrlShorteningService(urlShorteningService);
        urlShorteningController.setAuthenticationService(authenticationService);
        urlShorteningController.setCustomShortUrlService(customShortUrlService);
    }

    @Test
    public void testCreateShortUrl() {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("longUrl", "http://www.example.com");
        requestBody.put("customAlias", "example");

        UrlMapping urlMapping = new UrlMapping();
        urlMapping.setLongUrl("http://www.example.com");
        urlMapping.setShortUrl("http://api.ntoya.link/api/example");
        when(customShortUrlService.reserveCustomShortUrl("http://www.example.com", "example")).thenReturn(urlMapping);

        ResponseEntity<UrlMapping> response = urlShorteningController.createShortUrl(requestBody);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("http://www.example.com", response.getBody().getLongUrl());
        assertEquals("http://api.ntoya.link/api/example", response.getBody().getShortUrl());
    }

    @Test
    public void testCreateShortUrlWithoutCustomAlias() {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("longUrl", "http://www.example.com");

        UrlMapping urlMapping = new UrlMapping();
        urlMapping.setLongUrl("http://www.example.com");
        urlMapping.setShortUrl("http://api.ntoya.link/api/abcdef");
        when(urlShorteningService.createShortUrl("http://www.example.com")).thenReturn(urlMapping);

        ResponseEntity<UrlMapping> response = urlShorteningController.createShortUrl(requestBody);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("http://www.example.com", response.getBody().getLongUrl());
        assertEquals("http://api.ntoya.link/api/abcdef", response.getBody().getShortUrl());
    }

    @Test
    void testGetAllUrls() {
        // Arrange
        UrlMapping urlMapping1 = new UrlMapping("http://longurlexample.com", "abc123");
        UrlMapping urlMapping2 = new UrlMapping("http://google.com", "xyz456");
        List<UrlMapping> urlMappings = new ArrayList<>();
        urlMappings.add(urlMapping1);
        urlMappings.add(urlMapping2);
        Mockito.when(urlShorteningService.getAllUrlMappings()).thenReturn(urlMappings);

        // Act
        ResponseEntity<List<UrlMapping>> response = urlShorteningController.getAllUrls();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(urlMappings, response.getBody());
    }
    
    @Test
    void testRedirectToLongUrl() throws IOException {
        // Mock the response object
        MockHttpServletResponse response = new MockHttpServletResponse();

        // Create a mock URL mapping
        UrlMapping mockMapping = new UrlMapping();
        mockMapping.setLongUrl("http://www.example.com");

        // Mock the URL shortening service to return the mock URL mapping
        UrlShorteningService mockService = Mockito.mock(UrlShorteningService.class);
        Mockito.when(mockService.getLongUrl(Mockito.anyString())).thenReturn(mockMapping);

        // Create the controller to be tested and call the method
        UrlShorteningController controller = new UrlShorteningController();
        controller.setUrlShorteningService(mockService);
        controller.redirectToLongUrl("abc", response);

        // Assert that the response status is 302 (found) and that the redirect URL is correct
        assert (response.getStatus() == HttpServletResponse.SC_FOUND);
        assert (response.getRedirectedUrl().equals(mockMapping.getLongUrl()));

        // Mock the URL shortening service to return null
        Mockito.when(mockService.getLongUrl(Mockito.anyString())).thenReturn(null);

        // Call the method again with a non-existent short URL
        response = new MockHttpServletResponse();
        controller.redirectToLongUrl("xyz", response);

        // Assert that the response status is 404 (not found)
        assert (response.getStatus() == HttpServletResponse.SC_NOT_FOUND);
    }
}