package it.unicam.cs.mpgc.jtime119200.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a single day in the calendar domain.
 * A Day is identified by a specific {@link LocalDate} and
 * contains the list of {@link Activity} instances scheduled
 * for that date.
 * Responsibilities:
 *  - Store the date it represents
 *  - Maintain the collection of activities scheduled for that day
 *  - Provide read-only access to its activities
 */
public class Day {

    private final LocalDate date;
    private final List<Activity> activities = new ArrayList<>();

    /**
     * Constructs a Day associated with a specific date.
     *
     * @param date the {@link LocalDate} represented by this day
     */
    public Day(LocalDate date) {
        this.date = date;
    }

    /**
     * Returns the date represented by this day.
     *
     * @return the {@link LocalDate} of this day
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Returns an unmodifiable view of the activities scheduled for this day.
     *
     * @return an unmodifiable list of {@link Activity}
     */
    public List<Activity> getActivities() {
        return Collections.unmodifiableList(activities);
    }

    /**
     * Adds an activity to this day.
     *
     * @param activity the {@link Activity} to add
     */
    void addActivity(Activity activity) {
        activities.add(activity);
    }

    /**
     * Removes an activity from this day.
     *
     * @param activity the {@link Activity} to remove
     */
    public void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    /**
     * Returns the date as a string in ISO-8601 format.
     *
     * @return the string representation of the date
     */
    @Override
    public String toString() {
        return this.date.toString();
    }
}