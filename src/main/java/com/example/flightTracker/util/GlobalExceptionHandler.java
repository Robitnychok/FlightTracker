package com.example.flightTracker.util;

import com.example.flightTracker.dto.FlightSearchRequestDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.Map;

/**
 * Global exception handler that catches various exceptions and provides custom responses.
 * This class uses Spring's @ControllerAdvice to handle exceptions globally for all controllers.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles IllegalArgumentException and provides a custom response.
     * If the request came from the form, it adds the error to the model and returns the form view.
     * For other requests, it returns a JSON response with an error message.
     *
     * @param ex      the thrown IllegalArgumentException
     * @param model   Spring UI model used to pass attributes to the view
     * @param request the incoming HTTP request used to recognize the origin (form or API)
     * @return a view name for the form page or a ResponseEntity with JSON error message
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public Object handleIllegalArgumentException(IllegalArgumentException ex, Model model, HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        String formPath = "/api/flights/search";

        if (referer != null && referer.contains(formPath)) {
            model.addAttribute("flightSearchRequest", new FlightSearchRequestDTO());
            model.addAttribute("serviceError", ex.getMessage());
            return "flight/flight-form";
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap("error", ex.getMessage()));
    }

    /**
     * Handles DateTimeParseException when the user enters an invalid date/time format.
     *
     * @return a ResponseEntity with a BAD_REQUEST status and an error message about the expected format
     */
    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<Map<String, String>> handleDateTimeParseException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap("error", "Invalid date or time format: use YYYYMMDDHHMM (e.g., 202501011500)"));
    }

    /**
     * Handles HttpClientErrorException, typically when the external API returns a 4xx error.
     * If the request came from the form, it shows the error message in the form view.
     * Otherwise, it returns a JSON response with the error details.
     *
     * @param ex      the thrown HttpClientErrorException
     * @param model   Spring UI model used to pass attributes to the view
     * @param request the incoming HTTP request used to determine the origin (form or API)
     * @return a view name for the form page or a ResponseEntity with JSON error message
     */
    @ExceptionHandler(HttpClientErrorException.class)
    public Object handleHttpClientErrorException(HttpClientErrorException ex, Model model, HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        String formPath = "/api/flights/search";

        if (referer != null && referer.contains(formPath)) {
            model.addAttribute("flightSearchRequest", new FlightSearchRequestDTO());
            model.addAttribute("serviceError", "Departure not found");
            return "flight/flight-form";
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap("error", "Departure not found - " + ex.getResponseBodyAsString()));
    }
}

