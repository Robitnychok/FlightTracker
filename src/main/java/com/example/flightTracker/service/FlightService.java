package com.example.flightTracker.service;

import com.example.flightTracker.model.FlightDeparture;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Service class for interacting with the OpenSky API to get flight departure information.
 */
@Service
public class FlightService {

    private static final Logger logger = LoggerFactory.getLogger(FlightService.class);
    private static final int SEVEN_DAYS = 604800;

    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Gets flight departures from the OpenSky API for a given airport and time interval.
     * Results are cached based on the airport code and time interval.
     *
     * @param airportCode 4-letter ICAO code of the departure airport ("LKPR")
     * @param startTime UNIX timestamp for the beginning of the search interval
     * @param endTime UNIX timestamp for the end of the search interval
     * @return List of FlightDeparture
     * @throws IllegalArgumentException if input is invalid
     */
    @Cacheable(value = "flightDepartures", key = "#airportCode + '_' + #startTime + '_' + #endTime")
    public List<FlightDeparture> getFlightDepartures(String airportCode, Long startTime, Long endTime) {
        logger.info("Received request to get flight departures for airport={}, startTime={}, endTime={}",
                airportCode, startTime, endTime);

        if (airportCode == null ||!airportCode.matches("^[A-Z]{4}$")) {
            logger.error("Invalid airport code: {}", airportCode);
            throw new IllegalArgumentException("Airport code must be exactly 4 uppercase letters (e.g., EDDF)");
        }
        if (startTime >= endTime){
            logger.error("Start time is less than end time");
            throw new IllegalArgumentException("End time must be greater than start time");
        }
        if (endTime - startTime >= SEVEN_DAYS){
            logger.error("Time interval is more than 7 days: start - {}, end - {})", endTime, startTime);
            throw new IllegalArgumentException("The time interval must be less than 7 days");
        }
        long now = Instant.now().getEpochSecond();
        if (startTime > now || endTime > now) {
            logger.error("Time is in the future");
            throw new IllegalArgumentException("Departure and arrival times сan not be in the future");
        }

        logger.info("Caching flight departures for airport: {} from {} to {}", airportCode, startTime, endTime);

        String url = "https://opensky-network.org/api/flights/departure?airport=" + airportCode
                + "&begin=" + startTime
                + "&end=" + endTime;

        ResponseEntity<FlightDeparture[]> response = restTemplate.getForEntity(url, FlightDeparture[].class);

        FlightDeparture[] departures = response.getBody();

        if (departures != null) {
            logger.info("Received {} flight records", departures.length);
            return Arrays.asList(departures);
        } else {
            logger.warn("No flight departures found for the given parameters.");
            return new ArrayList<>();
        }
    }
}
