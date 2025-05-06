package com.example.flightTracker.model;

import lombok.Data;

/**
 * Represents a flight departure received from OpenSky API.
 */
@Data
public class FlightDeparture {

        private String icao24;
        private Long firstSeen;
        private String estDepartureAirport;
        private Long lastSeen;
        private String estArrivalAirport;
        private String callsign;
        private Integer estDepartureAirportHorizDistance;
        private Integer estDepartureAirportVertDistance;
        private Integer estArrivalAirportHorizDistance;
        private Integer estArrivalAirportVertDistance;
        private Integer departureAirportCandidatesCount;
        private Integer arrivalAirportCandidatesCount;
}
