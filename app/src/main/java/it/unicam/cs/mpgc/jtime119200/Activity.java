package it.unicam.cs.mpgc.jtime119200;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Objects;

public class Activity implements Comparable<Activity> {

    private final String title;
    private Project project;
    private  Duration expectedDuration;
    private Duration actualDuration;
    private boolean completed;
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

    public Instant getStartTime() {
        return startTime;
    }

    public boolean isCompleted() {
        return (status == ActivityStatus.COMPLETED);
    }
    // ----------setters-----------

    public void setExpectedDuration(Duration expectedDuration) {
        this.expectedDuration = expectedDuration;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public void setStatus(ActivityStatus status) {
        this.status = status;
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

    public void expire(){
        this.setStatus(ActivityStatus.EXPIRED);
    }

    public void expireIfNeeded() {
        if (status == ActivityStatus.PLANNED
                && startTime.atZone(ZoneId.systemDefault())
                .toLocalDate()
                .isBefore(LocalDate.now())) {
            status = ActivityStatus.EXPIRED;
        }
    }


    public Instant expectedEndTime() {
        return startTime.plus(expectedDuration);
    }

    public boolean overlaps(Activity other) {
        return this.startTime.isBefore(other.expectedEndTime())
                && other.getStartTime().isBefore(this.expectedEndTime());
    }

    public Duration estimationDifference() {
        if (!completed) {
            return Duration.ZERO;
        }
        return actualDuration.minus(expectedDuration);
    }

    public String activityDescription() {
        if (this.completed) {
            return "Started at " + this.getStartTime().atZone(ZoneId.of("UTC+1")).format(DateTimeFormatter.ofPattern("HH:mm")) + System.lineSeparator() +
                    "Ended at " + this.actualEndTime.atZone(ZoneId.of("UTC+1")).format(DateTimeFormatter.ofPattern("HH:mm")) + System.lineSeparator() +
                    "Actual Duration " + this.actualDuration.toMinutes() + System.lineSeparator() +
                    "Estimation difference: " + estimationDifference().toMinutes();
        } else
            return "Start Time " + this.getStartTime().atZone(ZoneId.of("UTC+1")).format(DateTimeFormatter.ofPattern("HH:mm")) + System.lineSeparator() +
                    "Expected End Time " + this.expectedEndTime().atZone(ZoneId.of("UTC+1")).format(DateTimeFormatter.ofPattern("HH:mm"));
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
