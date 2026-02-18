package it.unicam.cs.mpgc.jtime119200.domain;

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
     * Return the Day that contain the given Activity
     *
     * @param activity the activity we want to know the day
     * @return the Day of the Activity
     */
    public Day getDay(Activity activity) {
        Day dayOfActivity = null;
        for (Day day : days.values()) {
            if (day.getActivities().contains(activity)) dayOfActivity=day;
        }
        return dayOfActivity;
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
        if (projects.contains(project)) return;
        projects.add(project);
    }

    /**
     * Adds the given project in a string format to the calendar, and return that;
     * if the project already exist, return that.
     *
     * @param projectName the project name (string format) to add
     */
    public Project createProject(String projectName) {
        for (Project p : projects) {
            if (p.getName().equals(projectName)) {
                return p;
            }
        }
        Project newProject = new Project(projectName);
        projects.add(newProject);
        return newProject;
    }

    /**
     * @return an unmodifiable list of all projects
     */
    public List<Project> getProjects() {
        return Collections.unmodifiableList(projects);
    }

    /**
     * Removes the specified project and all the activities contained in that project.
     *
     * @param project the activity to remove
     */
    public void removeProject(Project project) {
        for (Activity activity : project.getActivities()) {
            removeActivity(project, activity);
        }
        projects.remove(project);
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
        Activity newActivity = new Activity(project, title, expectedDuration, startTime, date);
        for (Activity a : day.getActivities()) {
            if (a.overlaps(newActivity)) {
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
        Day day = days.get(activity.getDate());
            day.removeActivity(activity);
    }

    public void editActivity(Activity activity, Project project, Duration expectedDuration, Instant startTime) {

        Day day = getDay(activity.getDate());
        Activity candidate = new Activity(project, activity.getTitle(), expectedDuration, startTime, activity.getDate());
        for (Activity existing : day.getActivities()) {
            if (existing == activity) {
                continue;
            }
            if (existing.overlaps(candidate)) {
                throw new ActivityOverlapException("Activity overlaps another activity in the same day");
            }
        }
        activity.setProject(project);
        activity.setStartTime(startTime);
        activity.setExpectedDuration(expectedDuration);
    }

    /**
     * Update the status of all the activities of all days before today,
     * setting the status on EXPIRED
     */
    public void updateExpiredActivities() {
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



