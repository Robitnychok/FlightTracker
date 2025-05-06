package com.example.flightTracker.mapper;

import com.example.flightTracker.dto.FlightDepartureDTO;
import com.example.flightTracker.model.FlightDeparture;
import com.example.flightTracker.util.TimeUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

import org.mapstruct.Named;

import java.time.LocalDateTime;

/**
 * Mapper interface for converting FlightDeparture entities to DTOs.
 */
@Mapper(componentModel = "spring")
public interface FlightDepartureMapper {

    @Mappings({
            @Mapping(source = "icao24", target = "icao24Address"),
            @Mapping(source = "firstSeen", target = "departureTime", qualifiedByName = "unixToLocalDateTime"),
            @Mapping(source = "estDepartureAirport", target = "departureAirportIcao"),
            @Mapping(source = "lastSeen", target = "arrivalTime", qualifiedByName = "unixToLocalDateTime"),
            @Mapping(source = "estArrivalAirport", target = "arrivalAirportIcao"),
            @Mapping(source = "callsign", target = "flightCallSign"),
            @Mapping(source = "estDepartureAirportHorizDistance", target = "departureAirportHorizontalDistance"),
            @Mapping(source = "estDepartureAirportVertDistance", target = "departureAirportVerticalDistance"),
            @Mapping(source = "estArrivalAirportHorizDistance", target = "arrivalAirportHorizontalDistance"),
            @Mapping(source = "estArrivalAirportVertDistance", target = "arrivalAirportVerticalDistance")
    })
    FlightDepartureDTO mapFlightDepartureDTO(FlightDeparture flightDeparture);

    List<FlightDepartureDTO> mapFlightDepartures(List<FlightDeparture> flightDepartures);

    /**
     * Converts UNIX time to LocalDateTime in Czech time zone.
     *
     * @param unixTime UNIX timestamp
     * @return LocalDateTime in Czech time
     */
    @Named("unixToLocalDateTime")
    default LocalDateTime unixToFormattedDate(Long unixTime) {
        return TimeUtils.unixToLocalDateTime(unixTime);
    }
}
