package com.shorterurl.shorterurl.controller;

import com.shorterurl.shorterurl.model.Click;
import com.shorterurl.shorterurl.model.UrlMapping;
import com.shorterurl.shorterurl.service.AuthenticationService;
import com.shorterurl.shorterurl.service.ClickService;
import com.shorterurl.shorterurl.service.CustomShortUrlService;
import com.shorterurl.shorterurl.service.UrlShorteningService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

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

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    private UrlMapping testUrlMapping;

    @BeforeEach
    public void setUp() {
        testUrlMapping = new UrlMapping();
        testUrlMapping.setLongUrl("https://www.example.com");
        testUrlMapping.setShortUrl("shortUrl");
        testUrlMapping.setExpiration(LocalDateTime.now().plusDays(1));
    }

    @Test
    public void testCreateShortUrl() {
        when(authenticationService.isAuthorized(any())).thenReturn(false);
        when(urlShorteningService.createShortUrl(any(), any(), any())).thenReturn(testUrlMapping);

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("longUrl", "https://www.example.com");

        ResponseEntity<UrlMapping> responseEntity = urlShorteningController.createShortUrl(requestBody, request);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(testUrlMapping, responseEntity.getBody());
    }

    @Test
    public void testRedirectToLongUrl() throws IOException {
        when(urlShorteningService.getLongUrl(any())).thenReturn(testUrlMapping);

        urlShorteningController.redirectToLongUrl(testUrlMapping.getShortUrl(), request, response);

        verify(urlShorteningService, times(1)).saveUrlMapping(eq(testUrlMapping));
        verify(clickService, times(1)).saveClick(any(Click.class));
        verify(response, times(1)).sendRedirect(eq(testUrlMapping.getLongUrl()));
    }

    @Test
    public void testGetAllUrls() {
        when(urlShorteningService.getAllUrlMappings()).thenReturn(Collections.singletonList(testUrlMapping));

        ResponseEntity<List<UrlMapping>> responseEntity = urlShorteningController.getAllUrls();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(Collections.singletonList(testUrlMapping), responseEntity.getBody());
    }
}
