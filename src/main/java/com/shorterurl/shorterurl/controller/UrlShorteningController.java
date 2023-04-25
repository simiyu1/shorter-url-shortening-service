package com.shorterurl.shorterurl.controller;


import com.shorterurl.shorterurl.model.Click;
import com.shorterurl.shorterurl.model.UrlMapping;
import com.shorterurl.shorterurl.service.AuthenticationService;
import com.shorterurl.shorterurl.service.ClickService;
import com.shorterurl.shorterurl.service.CustomShortUrlService;
import com.shorterurl.shorterurl.service.UrlShorteningService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UrlShorteningController {

    @Autowired
    private UrlShorteningService urlShorteningService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private CustomShortUrlService customShortUrlService;

    @Autowired
    private ClickService clickService;
    

    // @Autowired
    // private LocationService locationService;

    public void setUrlShorteningService(UrlShorteningService urlShorteningService) {
        this.urlShorteningService = urlShorteningService;
    }
    public void setAuthenticationService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }
    public void setCustomShortUrlService(CustomShortUrlService customShortUrlService) {
        this.customShortUrlService = customShortUrlService;
    }
    public void setClickService(ClickService clickService) {
        this.clickService = clickService;
    }

    @PostMapping("/shorten")
    public ResponseEntity<UrlMapping> createShortUrl(
        @RequestBody Map<String, String> body,  HttpServletRequest request) {
        String customAlias = body.get("customAlias");
        String longUrl = body.get("longUrl");
        String ipAddress = body.get("ipAddress");

        String userId = null;
        String authorizationHeader = request.getHeader("Authorization");
        if (authenticationService.isAuthorized(authorizationHeader)) {
            userId = authenticationService.getUserId(authorizationHeader);
        }

        UrlMapping urlMapping;
        if (customAlias != null) {
            urlMapping = customShortUrlService.reserveCustomShortUrl(longUrl, customAlias, ipAddress, userId);
        } else {
            urlMapping = urlShorteningService.createShortUrl(longUrl, ipAddress, userId);
        }

        if (urlMapping == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(urlMapping, HttpStatus.CREATED);
    }

    @GetMapping("/{shortUrl}")
    public void redirectToLongUrl(String shortUrl, HttpServletRequest request, HttpServletResponse response) throws IOException {
        UrlMapping urlMapping = urlShorteningService.getLongUrl(shortUrl);

        LocalDateTime expiration = urlMapping != null ? urlMapping.getExpiration() : null;
        boolean isExpired = expiration == null || expiration.isBefore(LocalDateTime.now());

    
        if (urlMapping == null || isExpired || urlMapping.getExpiration().isBefore(LocalDateTime.now())) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
    
        // Increment the click count and save the UrlMapping
        urlMapping.setClickCount(urlMapping.getClickCount() + 1);
        urlShorteningService.saveUrlMapping(urlMapping);
    
        // Save the click data
        String ipAddress = request.getRemoteAddr();
        String location = null; // locationService.getLocationByIp(ipAddress);
        Click click = new Click(urlMapping.getId(), ipAddress, location);
        clickService.saveClick(click);
    
        response.sendRedirect(urlMapping.getLongUrl());
        int statusCode = response.getStatus();
        System.out.println("Redirect status code: " + statusCode);
    }

    // @GetMapping("/urls")
    // public ResponseEntity<List<UrlMapping>> getAllUrls(@RequestHeader(value = "Authorization") String authorizationToken){
    //     if (!authenticationService.isAuthorized(authorizationToken)) {
    //         return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    //     }
    
    //     List<UrlMapping> urlMappings = urlShorteningService.getAllUrlMappings();
    //     return new ResponseEntity<>(urlMappings, HttpStatus.OK);
    // }
    @GetMapping("/urls")
    public ResponseEntity<List<UrlMapping>> getAllUrls() {
        List<UrlMapping> urlMappings = urlShorteningService.getAllUrlMappings();
        return new ResponseEntity<>(urlMappings, HttpStatus.OK);
    }
}