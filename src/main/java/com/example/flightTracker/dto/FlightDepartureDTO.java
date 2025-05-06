package com.example.flightTracker.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Data Transfer Object representing a flight departure with readable field names.
 * Used to return flight data to the client.
 */
@Data
public class FlightDepartureDTO {

    private String icao24Address;
    private LocalDateTime departureTime;
    private String departureAirportIcao;

    private LocalDateTime arrivalTime;
    private String arrivalAirportIcao;

    private String flightCallSign;

    private Integer departureAirportHorizontalDistance;
    private Integer departureAirportVerticalDistance;

    private Integer arrivalAirportHorizontalDistance;
    private Integer arrivalAirportVerticalDistance;

    private Integer departureAirportCandidatesCount;
    private Integer arrivalAirportCandidatesCount;
}
