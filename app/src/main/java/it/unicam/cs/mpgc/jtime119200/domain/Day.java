package it.unicam.cs.mpgc.jtime119200.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//git to see
/**
 * Represents a day in the calendar. Each day can contain multiple activities.
 */

public class Day {

    private final LocalDate date;

    //List of Activities of the Day
    private final List<Activity> activities = new ArrayList<>();

    /**
     * Constructor of a Day
     * @param date corresponds to a date of the LocalDate class
     */
    public Day(LocalDate date) {
        this.date = date;
    }

    /**
     * Returns the date of this day.
     * @return the LocalDate of this day
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * @return the list of the activities of the Day
     */
    public List<Activity> getActivities() {
        return Collections.unmodifiableList(activities);
    }
    /**
     * Add the given Activity to the Day's Activities List
     * @param activity to add
     */
    void addActivity(Activity activity) {
        activities.add(activity);
    }
    /**
     * Remove the given Activity to the Day's Activities List
     * @param activity to remove
     */
    public void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    /**
     * Returns the date as a string  format (yyyy-MM-dd).
     * @return the date as a string
     */
    @Override
    public String toString() {
        return this.date.toString();
    }

}
