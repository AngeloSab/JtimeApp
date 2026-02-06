package it.unicam.cs.mpgc.jtime119200.application;

import it.unicam.cs.mpgc.jtime119200.domain.Activity;
import it.unicam.cs.mpgc.jtime119200.model.ActivityViewModel;

import java.time.Duration;
import java.util.function.Consumer;


import it.unicam.cs.mpgc.jtime119200.domain.ActivityStatus;
import it.unicam.cs.mpgc.jtime119200.domain.JtimeCalendar;
import it.unicam.cs.mpgc.jtime119200.domain.Project;
import it.unicam.cs.mpgc.jtime119200.model.ActivityReportViewModel;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

public class ActivityController {

    private final JtimeCalendar calendar;

    public ActivityController(JtimeCalendar calendar) {
        this.calendar = calendar;
    }

    // ---------------- Creazione ----------------
    public Activity createActivity(Project project, String title, Duration expectedDuration, Instant startTime, LocalDate date) {
        Activity activity = calendar.createActivity(project, title, expectedDuration, startTime, date);
        return activity;
    }

    // ---------------- Completamento ----------------
    public ActivityReportViewModel completeActivity(Activity activity, Duration actualDuration) {
        if (activity.getStatus() != ActivityStatus.PLANNED) {
            throw new IllegalStateException("Activity already completed or expired");
        }
        activity.complete(actualDuration);
        return new ActivityReportViewModel(activity);
    }

    // ---------------- Modifica ----------------
    public void editActivity(Activity activity, Project newProject, String newTitle, Duration newExpectedDuration, Instant newStartTime) {
        if (activity.getStatus() == ActivityStatus.COMPLETED) {
            throw new IllegalStateException("Cannot edit completed activity");
        }
        if (newStartTime != null) activity.setStartTime(newStartTime);
        if (newExpectedDuration != null) activity.setExpectedDuration(newExpectedDuration);
        if (newProject != null) activity.setProject(newProject);
    }

    // ---------------- Rimozione ----------------
    public void removeActivity(Activity activity) {
        calendar.removeActivity(activity.getProject(), activity);
    }

    // ---------------- Lista ----------------
    public List<Activity> getActivitiesForDay(LocalDate date) {
        return calendar.getDay(date).getActivities();
    }
}


