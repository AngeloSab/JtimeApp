package it.unicam.cs.mpgc.jtime119200.domain;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

public class Activity implements Comparable<Activity> {

    private final String title;
    private Project project;
    private  Duration expectedDuration;
    private Duration actualDuration;
    private  Instant startTime;
    private Instant actualEndTime;
    private ActivityStatus status;


    public Activity(Project project,
                    String title,
                    Duration expectedDuration,
                    Instant startTime) {

        this.project = Objects.requireNonNull(project);
        this.title = Objects.requireNonNull(title);
        this.expectedDuration = Objects.requireNonNull(expectedDuration);
        this.startTime = Objects.requireNonNull(startTime);
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

    public Instant getActualEndTime() {return actualEndTime;}

    public Instant getStartTime() {
        return startTime;
    }

    public boolean isCompleted() {
        return (status == ActivityStatus.COMPLETED);
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

    public void expire() {
            status = ActivityStatus.EXPIRED;
    }

    public Instant expectedEndTime() {
        return startTime.plus(expectedDuration);
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
