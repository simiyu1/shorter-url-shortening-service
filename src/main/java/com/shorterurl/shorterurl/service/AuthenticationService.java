package com.shorterurl.shorterurl.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Value("${app.authentication.api-key}")
    private String apiKey;

    public boolean isAuthorized(String authorizationToken) {
        if (authorizationToken == null) {
            return false;
        }

        String[] tokenParts = authorizationToken.split(" ");
        if (tokenParts.length != 2 || !tokenParts[0].equalsIgnoreCase("ApiKey")) {
            return false;
        }

        String providedApiKey = tokenParts[1];
        return apiKey.equals(providedApiKey);
    }
}