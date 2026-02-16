package it.unicam.cs.mpgc.jtime119200.domain.service;

import it.unicam.cs.mpgc.jtime119200.domain.Activity;
import it.unicam.cs.mpgc.jtime119200.domain.ActivityStatus;

import java.time.Duration;
import java.time.Instant;

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

    public Instant actualEndTime() {
        return activity.getStartTime().plus(activity.getActualDuration());
    }







}
