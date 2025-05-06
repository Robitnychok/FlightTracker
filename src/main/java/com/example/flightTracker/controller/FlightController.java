package com.example.flightTracker.controller;

import com.example.flightTracker.dto.FlightDepartureDTO;
import com.example.flightTracker.mapper.FlightDepartureMapper;
import com.example.flightTracker.model.FlightDeparture;
import com.example.flightTracker.service.FlightService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.example.flightTracker.util.TimeUtils.parseTimeToUnix;

/**
 * Controller for managing flight departures.
 * Provides an endpoint for getting flight departures based on airport and time interval.
 */
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class FlightController {

    private FlightService flightService;
    private FlightDepartureMapper flightDepartureMapper;

    /**
     * Endpoint to get flight departures from the given airport and time interval.
     *
     * @param airport The 4-letter ICAO code of the airport (e.g., "EDDF").
     * @param begin The start time in "yyyyMMddHHmm" format (e.g., "201801012000").
     * @param end The end time in "yyyyMMddHHmm" format (e.g., "201801012100").
     * @return A response containing a list of flight departures.
     */
    @GetMapping("/departure")
    public ResponseEntity<?> getFlightDepartures(
                                                  @RequestParam String airport,
                                                  @RequestParam String begin,
                                                  @RequestParam String end
    ) {

        Long startTimestamp = parseTimeToUnix(begin);
        Long endTimestamp = parseTimeToUnix(end);

        List<FlightDeparture> flightDepartures = flightService.getFlightDepartures(airport, startTimestamp, endTimestamp);
        List<FlightDepartureDTO> departures = flightDepartureMapper.mapFlightDepartures(flightDepartures);

        return ResponseEntity.ok(departures);
    }
}
