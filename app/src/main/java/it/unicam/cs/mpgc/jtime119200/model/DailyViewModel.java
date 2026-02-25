package it.unicam.cs.mpgc.jtime119200.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

/**
 * ViewModel representing a single day in the calendar.
 * Holds the date and a list of activities for that day.
 */
public class DailyViewModel {

    private final LocalDate date;
    private final List<ActivityViewModel> activities;

    /**
     * Constructs a DailyViewModel with the given date and activities.
     *
     * @param date the date of the day
     * @param activities list of activity view models for this day
     */
    public DailyViewModel(LocalDate date, List<ActivityViewModel> activities) {
        this.date = Objects.requireNonNull(date);
        this.activities = Objects.requireNonNull(activities);
    }

    /**
     * Returns the date of this day.
     *
     * @return the LocalDate of this day
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Returns the tooltip string to display when hovering over the day header.
     *
     * @return tooltip text
     */
    public String tooltipString() {
        return "Click to create a new Activity";
    }

    /**
     * Returns the list of activities for this day.
     *
     * @return list of ActivityViewModel objects
     */
    public List<ActivityViewModel> getActivities() {
        return activities;
    }

    /**
     * Returns a formatted label for the day, including the day of the week
     * abbreviation and the date in dd/MM/yyyy format.
     *
     * @return formatted date label
     */
    public String getDateLabel() {
        return date.getDayOfWeek().name().substring(0, 3) +" "+ date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))+" ";
    }
}