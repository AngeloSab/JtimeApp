package it.unicam.cs.mpgc.jtime119200.repository;

import it.unicam.cs.mpgc.jtime119200.domain.JtimeCalendar;

public interface CalendarRepository {

    void load(JtimeCalendar calendar);
    void save(JtimeCalendar calendar);
}