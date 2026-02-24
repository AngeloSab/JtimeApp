package it.unicam.cs.mpgc.jtime119200.domain.service;

import it.unicam.cs.mpgc.jtime119200.domain.Activity;
import it.unicam.cs.mpgc.jtime119200.domain.ActivityStatus;

import java.time.Duration;
import java.time.Instant;

/**
 * Utility class for calculating time-related properties of an {@link Activity}.
 * Provides methods to compute the difference between expected and actual durations,
 * and to determine the actual end time of a completed activity.
 */
public class ActivityTimeCalculator {

    private final Activity activity;

    /**
     * Constructs a new calculator for the specified activity.
     *
     * @param activity the activity to analyze
     */
    public ActivityTimeCalculator(Activity activity) {
        this.activity = activity;
    }

    /**
     * Returns the difference between the actual duration and the expected duration of the activity.
     *
     * @return a {@link Duration} representing actual minus expected duration
     * @throws IllegalStateException if the activity has not been completed
     */
    public Duration estimationDifference() {
        if (activity.getStatus() != ActivityStatus.COMPLETED) {
            throw new IllegalStateException("Activity not completed");
        }
        return activity.getActualDuration()
                .minus(activity.getExpectedDuration());
    }

    /**
     * Returns the actual end time of the activity, calculated as start time plus actual duration.
     *
     * @return an {@link Instant} representing the actual end time
     */
    public Instant actualEndTime() {
        return activity.getStartTime().plus(activity.getActualDuration());
    }
}