package it.unicam.cs.mpgc.jtime119200.domain.service;

import it.unicam.cs.mpgc.jtime119200.domain.Activity;
import it.unicam.cs.mpgc.jtime119200.domain.ActivityStatus;

import java.time.Duration;

public class ActivityTimeCalculator {

    private final Activity activity;

    public ActivityTimeCalculator(Activity activity) {
        this.activity = activity;
    }

    public Duration estimationDifference() {
        if (activity.getStatus() != ActivityStatus.COMPLETED) {
            throw new IllegalStateException("Activity not completed");
        }
        return activity.getActualDuration()
                .minus(activity.getExpectedDuration());
    }

    public boolean overlaps(Activity a1, Activity a2) {
        return a1.getStartTime().isBefore(a2.expectedEndTime())
                && a2.getStartTime().isBefore(a1.expectedEndTime());
    }

}
