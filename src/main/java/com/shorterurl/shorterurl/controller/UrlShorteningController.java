package com.shorterurl.shorterurl.controller;


import com.shorterurl.shorterurl.model.UrlMapping;
import com.shorterurl.shorterurl.service.AuthenticationService;
import com.shorterurl.shorterurl.service.CustomShortUrlService;
import com.shorterurl.shorterurl.service.UrlShorteningService;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
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

    @PostMapping("/shorten")
    public ResponseEntity<UrlMapping> createShortUrl(
        @RequestBody Map<String, String> body,
        @RequestParam(value = "customAlias", required = false) String customAlias,
                                                      @RequestHeader(value = "Authorization") String authorizationToken) {
        if (!authenticationService.isAuthorized(authorizationToken)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        String longUrl = body.get("longUrl");

        UrlMapping urlMapping;
        if (customAlias != null) {
            urlMapping = customShortUrlService.reserveCustomShortUrl(longUrl, customAlias);
        } else {
            urlMapping = urlShorteningService.createShortUrl(longUrl);
        }

        if (urlMapping == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(urlMapping, HttpStatus.CREATED);
    }

    @GetMapping("/{shortUrl}")
    public void redirectToLongUrl(@PathVariable("shortUrl") String shortUrl, HttpServletResponse response) {
        UrlMapping urlMapping = urlShorteningService.getLongUrl(shortUrl);
        if (urlMapping != null) {
            try {
                response.sendRedirect(urlMapping.getLongUrl());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @GetMapping("/urls")
    public ResponseEntity<List<UrlMapping>> getAllUrls(@RequestHeader(value = "Authorization") String authorizationToken){
        if (!authenticationService.isAuthorized(authorizationToken)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    
        List<UrlMapping> urlMappings = urlShorteningService.getAllUrlMappings();
        return new ResponseEntity<>(urlMappings, HttpStatus.OK);
    }
}