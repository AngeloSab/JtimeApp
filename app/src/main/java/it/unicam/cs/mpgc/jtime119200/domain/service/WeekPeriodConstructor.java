package it.unicam.cs.mpgc.jtime119200.domain.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Constructs a weekly period based on a given offset.
 * This class generates a list of seven consecutive days starting
 * from a calculated base date. The base date is determined by
 * applying a week offset to the current date.
 * An offset of 0 represents the current week starting from today,
 * a positive offset represents future weeks, and a negative offset
 * represents past weeks.
 */
public class WeekPeriodConstructor {
    private final int offset;
    private final LocalDate today = LocalDate.now();

    /**
     * Creates a new week period constructor with the specified offset.
     *
     * @param offset the number of weeks to shift from the current date
     */
    public WeekPeriodConstructor(int offset) {this.offset = offset;}

    /**
     * Returns a list of seven consecutive dates representing the week.
     * The first day is calculated by adding (offset * 7) days to today.
     *
     * @return a list containing seven LocalDate objects
     */
    public List<LocalDate> getDays() {
        LocalDate start = today.plusDays(offset * 7L);
        List<LocalDate> days = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            days.add(start.plusDays(i));
        }
        return days;
    }
}