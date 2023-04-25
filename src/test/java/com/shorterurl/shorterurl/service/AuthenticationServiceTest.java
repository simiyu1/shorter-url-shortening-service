package com.shorterurl.shorterurl.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AuthenticationServiceTest {
    private AuthenticationService authenticationService;
    private final String properApiKey = "proper-api-key";

    @BeforeEach
    public void setUp() {
        authenticationService = new AuthenticationService();
        ReflectionTestUtils.setField(authenticationService, "apiKey", properApiKey);
    }

    @Test
    public void testIsAuthorized_nullToken() {
        assertFalse(authenticationService.isAuthorized(null));
    }

    @Test
    public void testIsAuthorized_invalidTokenFormat() {
        assertFalse(authenticationService.isAuthorized("InvalidTokenFormat"));
        assertFalse(authenticationService.isAuthorized("ApiKey"));
    }

    @Test
    public void testIsAuthorized_incorrectApiKey() {
        String incorrectApiKey = "incorrect-api-key";
        assertFalse(authenticationService.isAuthorized("ApiKey " + incorrectApiKey));
    }

    @Test
    public void testIsAuthorized_correctApiKey() {
        assertTrue(authenticationService.isAuthorized("ApiKey " + properApiKey));
    }
}
