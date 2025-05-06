package com.example.flightTracker.controller;

import com.example.flightTracker.dto.FlightDepartureDTO;
import com.example.flightTracker.dto.FlightSearchRequestDTO;
import com.example.flightTracker.mapper.FlightDepartureMapper;
import com.example.flightTracker.model.FlightDeparture;
import com.example.flightTracker.service.FlightService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static com.example.flightTracker.util.TimeUtils.convertIsoToUnix;

@Controller
@RequestMapping("/api")
@AllArgsConstructor
public class WebFlightController {

    private static final Logger logger = LoggerFactory.getLogger(WebFlightController.class);

    private FlightService flightService;
    private FlightDepartureMapper flightDepartureMapper;

    /**
     * Displays the search form for flight departures.
     *
     * @param model The model to hold the form data for the view.
     * @return Flight search form.
     */
    @GetMapping("/flights/form")
    public String showSearchForm(Model model) {
        model.addAttribute("flightSearchRequest", new FlightSearchRequestDTO());
        return "flight/flight-form";
    }

    /**
     * Handles the flight search based on user input.
     * Validates the request, gets flight data, and returns the results.
     *
     * @param request The user input the search parameters.
     * @param bindingResult The result of validation checks.
     * @param model The model to pass data to the view.
     * @return Flight search results form.
     */
    @PostMapping("/flights/search")
    public String searchFlights(@ModelAttribute("flightSearchRequest") @Valid  FlightSearchRequestDTO request,
                                BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            logger.warn("Validation errors occurred for flight search request: {}", request);
            return "flight/flight-form";
        }

        Long startTimestamp = convertIsoToUnix(String.valueOf(request.getDepartureTime()));
        Long endTimestamp = convertIsoToUnix(String.valueOf(request.getArrivalTime()));

        List<FlightDeparture> flights = flightService.getFlightDepartures(
                    request.getDepartureAirportIcao(), startTimestamp, endTimestamp);
        List<FlightDepartureDTO> flightsDTO = flightDepartureMapper.mapFlightDepartures(flights);

        model.addAttribute("flights", flightsDTO);
            return "flight/flights-result";
    }
}