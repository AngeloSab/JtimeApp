package it.unicam.cs.mpgc.jtime119200.domain;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

public class Activity implements Comparable<Activity> {

    private final String title;
    private Project project;
    private  Duration expectedDuration;
    private  Instant startTime;
    private LocalDate date;

    private Duration actualDuration;
    private Instant actualEndTime;
    private ActivityStatus status;


    public Activity(Project project, String title, Duration expectedDuration, Instant startTime, LocalDate date) {

        this.project = Objects.requireNonNull(project);
        this.title = Objects.requireNonNull(title);
        this.expectedDuration = Objects.requireNonNull(expectedDuration);
        this.startTime = Objects.requireNonNull(startTime);
        this.date = date;

        this.status = ActivityStatus.PLANNED;
    }

    // ---------- getters ----------

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

    public boolean isCompleted() {return (status == ActivityStatus.COMPLETED);}

    public boolean isExpired() {return (status == ActivityStatus.EXPIRED);}

    public LocalDate getDate() {
        return date;
    }
    // ----------setters-----------

    public void setExpectedDuration(Duration expectedDuration) {this.expectedDuration = expectedDuration;
    }

    public void setStartTime(Instant startTime) {

        this.startTime = startTime;
    }

    public void setProject(Project project) {
        this.project = project;
    }


    // ---------- behavior ----------

    public void complete(Duration actualDuration) {
        if (status == ActivityStatus.COMPLETED) {
            throw new IllegalStateException("Activity already completed");
        }
        this.actualDuration = actualDuration;
        this.actualEndTime = startTime.plus(actualDuration);
        this.status = ActivityStatus.COMPLETED;
    }

    public Instant expectedEndTime() {
        return this.getStartTime().plus(this.getExpectedDuration());
    }

    public boolean overlaps(Activity other) {
        return this.getStartTime().isBefore(other.expectedEndTime())
                && other.getStartTime().isBefore(this.expectedEndTime());
    }

    public void expire() {
            status = ActivityStatus.EXPIRED;
    }

    @Override
    public String toString() {
        return  this.title;
    }

    @Override
    public int compareTo(Activity o) {
        return this.startTime.compareTo(o.startTime);
    }
}
