package com.example.flightTracker.util;

import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * Helper class for working with time. Provides methods to convert between UNIX timestamps, string formats,
 * and LocalDateTime, using the Czech time zone (Europe/Prague).
 */
public class TimeUtils {

    private static final ZoneId CZECH_ZONE = ZoneId.of("Europe/Prague");

    /**
     * Converts UNIX time to LocalDateTime in Czech time zone.
     *
     * @param unixTime UNIX timestamp
     * @return LocalDateTime
     */
    public static LocalDateTime unixToLocalDateTime(Long unixTime) {
        if (unixTime == null) return null;
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(unixTime), CZECH_ZONE);
    }

    /**
     * Converts a string in the format yyyyMMddHHmm to UNIX time.
     *
     * @param time time string in the format yyyyMMddHHmm
     * @return UNIX timestamp
     */
    public static long parseTimeToUnix(String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        LocalDateTime dateTime = LocalDateTime.parse(time, formatter);

        return dateTime.atZone(CZECH_ZONE).toEpochSecond();
    }

    /**
     *  Converts a string in the format ISO 2025-05-02T15:21 (from HTML input[type="datetime-local"]) to UNIX time.
     *
     * @param isoTime ISO datetime string in the format yyyy-MM-dd'T'HH:mm
     * @return UNIX timestamp
     */
    public static long convertIsoToUnix(String isoTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime dateTime = LocalDateTime.parse(isoTime, formatter);

        return dateTime.atZone(CZECH_ZONE).toEpochSecond();
    }
}
