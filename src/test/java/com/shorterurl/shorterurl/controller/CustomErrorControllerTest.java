package com.shorterurl.shorterurl.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class CustomErrorControllerTest {
    private CustomErrorController customErrorController;
    private HttpServletRequest mockRequest;
    private Model mockModel;

    @BeforeEach
    public void setUp() {
        customErrorController = new CustomErrorController();
        mockRequest = Mockito.mock(HttpServletRequest.class);
        mockModel = Mockito.mock(Model.class);
    }

    @Test
    public void handleError_NotFound() {
        when(mockRequest.getAttribute(jakarta.servlet.RequestDispatcher.ERROR_STATUS_CODE))
                .thenReturn(HttpStatus.NOT_FOUND.value());

        String result = customErrorController.handleError(mockRequest, mockModel);

        assertEquals("error", result);
    }

    @Test
    public void handleError_InternalServerError() {
        when(mockRequest.getAttribute(jakarta.servlet.RequestDispatcher.ERROR_STATUS_CODE))
                .thenReturn(HttpStatus.INTERNAL_SERVER_ERROR.value());

        String result = customErrorController.handleError(mockRequest, mockModel);

        assertEquals("error", result);
    }

    @Test
    public void handleError_BadRequest() {
        when(mockRequest.getAttribute(jakarta.servlet.RequestDispatcher.ERROR_STATUS_CODE))
                .thenReturn(HttpStatus.BAD_REQUEST.value());

        String result = customErrorController.handleError(mockRequest, mockModel);

        assertEquals("error", result);
    }

    @Test
    public void handleError_OtherError() {
        int otherErrorCode = 418; // Example: I'm a teapot
        when(mockRequest.getAttribute(jakarta.servlet.RequestDispatcher.ERROR_STATUS_CODE))
                .thenReturn(otherErrorCode);

        String result = customErrorController.handleError(mockRequest, mockModel);

        assertEquals("error", result);
    }
}
