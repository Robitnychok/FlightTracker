package com.example.flightTracker.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for flight search request.
 * Contains input data from the user from a form.
 */
@Data
public class FlightSearchRequestDTO {

    @NotBlank(message = "Airport ICAO code is required")
    @Size(min = 4, max = 4, message = "ICAO code must be 4 letters")
    @Pattern(regexp = "^[A-Z]{4}$", message = "ICAO code must contain only uppercase letters")
    private String departureAirportIcao;

    @NotNull(message = "Departure time is required")
    @PastOrPresent(message = "Departure time cannot be in the future")
    private LocalDateTime departureTime;

    @NotNull(message = "Arrival time is required")
    @PastOrPresent(message = "Arrival time cannot be in the future")
    private LocalDateTime arrivalTime;
}
