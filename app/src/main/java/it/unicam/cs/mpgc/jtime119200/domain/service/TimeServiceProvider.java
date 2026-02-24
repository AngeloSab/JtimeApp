package it.unicam.cs.mpgc.jtime119200.domain.service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Provides time-related utilities for the application.
 * This service centralizes time zone management and formatting logic,
 * ensuring consistent time representation across the system.
 * It currently formats time using the Europe/Rome time zone and
 * the pattern HH:mm.
 */
public class TimeServiceProvider {

    private final DateTimeFormatter hoursMins = DateTimeFormatter.ofPattern("HH:mm");

    /**
     * Returns the time zone used by the application.
     *
     * @return the ZoneId representing Europe/Rome
     */
    public ZoneId getTimeZone(){
        return ZoneId.of("Europe/Rome");
    }

    /**
     * Formats the given instant according to the application time zone
     * and the HH:mm pattern.
     *
     * @param instant the instant to format
     * @return a formatted time string
     */
    public String getFormattedTime(Instant instant){
        return instant.atZone(getTimeZone()).format(hoursMins);
    }
}