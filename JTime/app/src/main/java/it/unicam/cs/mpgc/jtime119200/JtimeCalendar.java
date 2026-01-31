package it.unicam.cs.mpgc.jtime119200;

import java.time.LocalDate;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class JtimeCalendar {

    private final Map<LocalDate, Day> days = new HashMap<>();
    private final List<Project> projects = new ArrayList<>();

    // ================== DAYS ==================

    public Day getDay(LocalDate date) {
        return days.computeIfAbsent(date, Day::new);
    }

    public Collection<Day> getAllDays() {
        return Collections.unmodifiableCollection(days.values());
    }

    // ================== PROJECTS ==================

    public void addProject(Project project) {
        if (projects.contains(project)) {
            throw new IllegalArgumentException("Project already exists");
        }
        projects.add(project);
    }

    public List<Project> getProjects() {
        return Collections.unmodifiableList(projects);
    }

    // ================== ACTIVITIES ==================

    public Activity createActivity(Project project, String title, Duration expectedDuration, Instant startTime, LocalDate date) {
        Objects.requireNonNull(project);
        Day day = getDay(date);
        Activity newActivity = new Activity(project, title, expectedDuration, startTime);
        // no overlaps
        for (Activity a : day.getActivities()) {
            if (newActivity.overlaps(a)) {
                throw new ActivityOverlapException(
                        "Activity overlaps another activity in the same day"
                );
            }
        }

        day.addActivity(newActivity);
        project.addActivity(newActivity);

        return newActivity;
    }

    public void removeActivity(Project project, Activity activity) {
        project.removeActivity(activity);

        for (Day day : days.values()) {
            day.removeActivity(activity);
        }
    }

    public void expiresActivity(){
        for (Day day : this.getAllDays()) {
            for (Activity a : day.getActivities()) {
                a.expireIfNeeded();
            }
        }
    }
}



