package it.unicam.cs.mpgc.jtime119200.application;

import it.unicam.cs.mpgc.jtime119200.domain.Activity;
import it.unicam.cs.mpgc.jtime119200.domain.JtimeCalendar;
import it.unicam.cs.mpgc.jtime119200.domain.Project;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;

public class ActivityService {

    private final JtimeCalendar calendar;

    public ActivityService(JtimeCalendar calendar) {
        this.calendar = calendar;
    }

    public void completeActivity(Activity activity, Duration actualDuration) {
        activity.complete(actualDuration);
    }

    public void expireActivity(Activity activity) {
        activity.expire();
    }

    public void updateActivity(Activity activity,
                               Duration expectedDuration,
                               Instant startTime) {
        activity.setExpectedDuration(expectedDuration);
        activity.setStartTime(startTime);
    }

    public void removeActivity(Activity activity) {
        calendar.removeActivity(activity.getProject(), activity);
    }

    public Activity createActivity(Project project, String title, Duration expectedDuration, Instant startTime, LocalDate date) {
        return calendar.createActivity(project, title, expectedDuration, startTime, date);
    }
}
