package it.unicam.cs.mpgc.jtime119200.repository;

import it.unicam.cs.mpgc.jtime119200.domain.JtimeCalendar;

/**
 * Interface defining persistence operations for a JtimeCalendar.
 * Implementations are responsible for saving and loading calendar data from a storage system.
 */
public interface CalendarRepository {

    /**
     * Loads data into the given calendar from persistent storage.
     *
     * @param calendar the calendar instance to populate
     */
    void load(JtimeCalendar calendar);

    /**
     * Saves the state of the given calendar to persistent storage.
     *
     * @param calendar the calendar instance to save
     */
    void save(JtimeCalendar calendar);
}