package it.unicam.cs.mpgc.jtime119200.domain;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;

/**
 * Represents a calendar for managing projects and activities over multiple days.
 * A JtimeCalendar is responsible for:
 *  - Managing {@link Day} instances corresponding to specific dates
 *  - Managing {@link Project} instances
 *  - Managing {@link Activity} instances associated with projects and days
 *  - Ensuring activities do not overlap within the same day
 *  - Providing unmodifiable access to its internal collections
 */
public class JtimeCalendar {

    private final Map<LocalDate, Day> days = new HashMap<>();
    private final List<Project> projects = new ArrayList<>();

    /**
     * Returns the {@link Day} associated with the given date.
     * If no day exists for that date, a new one is created and returned.
     *
     * @param date the date for which to retrieve the day
     * @return the {@link Day} for the specified date
     */
    public Day getDay(LocalDate date) {
        return days.computeIfAbsent(date, Day::new);
    }

    /**
     * Returns an unmodifiable collection of all {@link Day} instances in the calendar.
     *
     * @return an unmodifiable collection of days
     */
    public Collection<Day> getAllDays() {
        return Collections.unmodifiableCollection(days.values());
    }

    /**
     * Adds a {@link Project} to the calendar.
     * If the project already exists, it is ignored.
     *
     * @param project the project to add
     */
    public void addProject(Project project) {
        if (projects.contains(project)) return;
        projects.add(project);
    }

    /**
     * Creates a new {@link Project} with the given name if it does not already exist.
     * If a project with the same name exists, returns that project.
     *
     * @param projectName the name of the project
     * @return the existing or newly created project
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
     * Returns an unmodifiable list of all projects in the calendar.
     *
     * @return an unmodifiable list of {@link Project}
     */
    public List<Project> getProjects() {
        return Collections.unmodifiableList(projects);
    }

    /**
     * Removes a project and all its associated activities from the calendar.
     *
     * @param project the project to remove
     */
    public void removeProject(Project project) {
        for (Activity activity : project.getActivities()) {
            removeActivity(project, activity);
        }
        projects.remove(project);
    }

    /**
     * Creates a new {@link Activity} in the calendar.
     * Throws {@link ActivityOverlapException} if the new activity overlaps
     * with an existing activity on the same day.
     *
     * @param project the project this activity belongs to
     * @param title the title of the activity
     * @param expectedDuration the expected duration
     * @param startTime the start time of the activity
     * @param date the date of the activity
     * @return the newly created {@link Activity}
     * @throws ActivityOverlapException if the activity overlaps another on the same day
     */
    public Activity createActivity(Project project, String title, Duration expectedDuration, Instant startTime, LocalDate date) {
        Day day = getDay(date);
        Activity newActivity = new Activity(project, title, expectedDuration, startTime, date);
        for (Activity a : day.getActivities()) {
            if (a.overlaps(newActivity)) {
                throw new ActivityOverlapException("Activity overlaps another activity in the same day");
            }
        }
        day.addActivity(newActivity);
        project.addActivity(newActivity);
        return newActivity;
    }

    /**
     * Removes an activity from a specific project and from its day.
     *
     * @param project the project containing the activity
     * @param activity the activity to remove
     */
    public void removeActivity(Project project, Activity activity) {
        project.removeActivity(activity);
        Day day = days.get(activity.getDate());
        day.removeActivity(activity);
    }

    /**
     * Updates an existing activity's project, start time, and expected duration.
     * Throws {@link ActivityOverlapException} if the updated activity overlaps
     * another activity on the same day.
     *
     * @param activity the activity to edit
     * @param project the new project for the activity
     * @param expectedDuration the new expected duration
     * @param startTime the new start time
     */
    public void editActivity(Activity activity, Project project, Duration expectedDuration, Instant startTime) {
        Day day = getDay(activity.getDate());
        Activity candidate = new Activity(project, activity.getTitle(), expectedDuration, startTime, activity.getDate());
        for (Activity existing : day.getActivities()) {
            if (existing == activity) continue;
            if (existing.overlaps(candidate)) {
                throw new ActivityOverlapException("Activity overlaps another activity in the same day");
            }
        }
        activity.setProject(project);
        activity.setStartTime(startTime);
        activity.setExpectedDuration(expectedDuration);
    }

    /**
     * Updates all past activities, marking them as {@link ActivityStatus#EXPIRED}
     * if they are still planned.
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