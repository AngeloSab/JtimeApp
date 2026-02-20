package it.unicam.cs.mpgc.jtime119200.domain.service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class TimeServiceProvider {

    private final DateTimeFormatter hoursMins = DateTimeFormatter.ofPattern("HH:mm");


    public ZoneId getTimeZone(){
        return ZoneId.of("Europe/Rome");
    }

    public String getFormattedTime(Instant instant){
        return instant.atZone(getTimeZone()).format(hoursMins);
    }
}
