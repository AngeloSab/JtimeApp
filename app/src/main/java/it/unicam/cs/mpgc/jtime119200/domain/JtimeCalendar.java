package it.unicam.cs.mpgc.jtime119200.domain;

import it.unicam.cs.mpgc.jtime119200.domain.service.ActivityTimeCalculator;

import java.time.LocalDate;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

/**
 * Represents a calendar for managing projects and activities over multiple days.
 * This class provides methods to:
 * Manage {@link Day} objects for specific dates.
 * Manage {@link Project} objects.
 * Manage {@link Activity} objects associated with projects and days.
 * The calendar ensures that activities do not overlap, and it
 * maintains encapsulation by providing unmodifiable views of its internal collections.
 */

public class JtimeCalendar {

    private final Map<LocalDate, Day> days = new HashMap<>();
    private final List<Project> projects = new ArrayList<>();

    // ================== DAYS ==================

    /**
     * Returns the {@link Day} associated with the given date.
     * If no Day exists for that date, a new one is created and returned.
     *
     * @param date the date for which to retrieve the Day
     * @return the Day associated with the given date
     */

    public Day getDay(LocalDate date) {
        return days.computeIfAbsent(date, Day::new);
    }

    /**
     * @return an unmodifiable collection of all {@link Day} instances
     */

    public Collection<Day> getAllDays() {
        return Collections.unmodifiableCollection(days.values());
    }

    // ================== PROJECTS ==================

    /**
     * Adds the given project to the calendar.
     *
     * @param project the project to add
     * @throws IllegalArgumentException if the project already exists
     */
    public void addProject(Project project) {
        if (projects.contains(project)) {
            throw new IllegalArgumentException("Project already exists");
        }
        projects.add(project);
    }

    /**
     * @return an unmodifiable list of all projects
     */
    public List<Project> getProjects() {
        return Collections.unmodifiableList(projects);
    }

    // ================== ACTIVITIES ==================
    /**
     * Create a new Activity and check if there is overlaps:
     * if an Activity already exists in the same day, it throws an ActivityOverlapException.
     * It uses the addActivity() method of the Activity and Project classes.
     * @param project the project this activity belongs to
     * @param title the title of the activity
     * @param expectedDuration the expected duration of the activity
     * @param startTime the start time of the activity
     * @param date the date for which the activity is scheduled
     * @return the newly created Activity
     * @throws ActivityOverlapException if the activity overlaps another activity on the same day
     */
    public Activity createActivity(Project project, String title, Duration expectedDuration, Instant startTime, LocalDate date) {
        Day day = getDay(date);
        Activity newActivity = new Activity(project, title, expectedDuration, startTime);
        ActivityTimeCalculator atc = new ActivityTimeCalculator(newActivity);
        for (Activity a : day.getActivities()) {
            if (atc.overlaps(newActivity,a)) {
                throw new ActivityOverlapException(
                        "Activity overlaps another activity in the same day"
                );
            }
        }
        day.addActivity(newActivity);
        project.addActivity(newActivity);
        return newActivity;
    }

    /**
     * Removes the specified activity from the given project and from all days in the calendar.
     *
     * @param project the project from which the activity should be removed
     * @param activity the activity to remove
     */

    public void removeActivity(Project project, Activity activity) {
        project.removeActivity(activity);
        for (Day day : days.values()) {
            day.removeActivity(activity);
        }
    }

    /**
     * Update the status of all the activities of all days before today,
     * setting the status on EXPIRED
     */
    public void updateExpiredAcitvities() {
        for (Day day : days.values()) {
            if (day.getDate().isBefore(LocalDate.now())) {
                for (Activity a : day.getActivities()) {
                    if (a.getStatus() == ActivityStatus.PLANNED) {
                        a.expire();
                    }
                }
            }
        }
    }

}



