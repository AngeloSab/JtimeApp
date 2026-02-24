package it.unicam.cs.mpgc.jtime119200.domain;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;

/**
 * Represents an activity within a project on a specific date.
 * Each activity has a title, associated project, expected duration,
 * start time, actual duration (if completed), and a status that can
 * be PLANNED, COMPLETED, or EXPIRED.
 * Implements {@link Comparable} to allow sorting activities by start time.
 * Responsibilities:
 *  - Track expected and actual durations
 *  - Track start time and date
 *  - Determine completion and expiration
 *  - Check for overlapping with other activities
 */
public class Activity implements Comparable<Activity> {

    private final String title;
    private Project project;
    private Duration expectedDuration;
    private Instant startTime;
    private final LocalDate date;

    private Duration actualDuration;
    private ActivityStatus status;

    /**
     * Constructs a new Activity.
     *
     * @param project the project this activity belongs to
     * @param title the activity title
     * @param expectedDuration the planned duration of the activity
     * @param startTime the planned start time of the activity
     * @param date the date on which the activity occurs
     */
    public Activity(Project project, String title, Duration expectedDuration, Instant startTime, LocalDate date) {
        this.project = project;
        this.title = title;
        this.expectedDuration = expectedDuration;
        this.startTime = startTime;
        this.date = date;
        this.status = ActivityStatus.PLANNED;
    }

    public Project getProject() {
        return project;
    }

    public ActivityStatus getStatus() {
        return status;
    }

    public String getTitle() {
        return title;
    }

    public Duration getExpectedDuration() {
        return expectedDuration;
    }

    public Duration getActualDuration() {
        return actualDuration;
    }

    public Instant getStartTime() {
        return startTime;
    }

    /**
     * Checks if the activity is completed.
     *
     * @return true if the status is COMPLETED, false otherwise
     */
    public boolean isCompleted() {
        return (status == ActivityStatus.COMPLETED);
    }

    /**
     * Checks if the activity is expired.
     *
     * @return true if the status is EXPIRED, false otherwise
     */
    public boolean isExpired() {
        return (status == ActivityStatus.EXPIRED);
    }

    public LocalDate getDate() {
        return date;
    }

    public void setExpectedDuration(Duration expectedDuration) {
        this.expectedDuration = expectedDuration;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    /**
     * Marks the activity as completed and sets its actual duration.
     *
     * @param actualDuration the duration it actually took to complete
     * @throws IllegalStateException if the activity is already completed
     */
    public void complete(Duration actualDuration) {
        if (status == ActivityStatus.COMPLETED) {
            throw new IllegalStateException("Activity already completed");
        }
        this.actualDuration = actualDuration;
        this.status = ActivityStatus.COMPLETED;
    }

    /**
     * Calculates the expected end time of the activity.
     *
     * @return the expected end time based on start time and expected duration
     */
    public Instant expectedEndTime() {
        return this.getStartTime().plus(this.getExpectedDuration());
    }

    /**
     * Checks if this activity overlaps with another activity.
     *
     * @param other another activity to compare with
     * @return true if the time intervals overlap, false otherwise
     */
    public boolean overlaps(Activity other) {
        return this.getStartTime().isBefore(other.expectedEndTime())
                && other.getStartTime().isBefore(this.expectedEndTime());
    }

    /**
     * Marks the activity as expired.
     */
    public void expire() {
        status = ActivityStatus.EXPIRED;
    }

    @Override
    public String toString() {
        return this.title;
    }

    @Override
    public int compareTo(Activity o) {
        return this.startTime.compareTo(o.startTime);
    }
}