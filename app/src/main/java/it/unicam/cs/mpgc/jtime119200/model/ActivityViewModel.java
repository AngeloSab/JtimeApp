package it.unicam.cs.mpgc.jtime119200.model;

import it.unicam.cs.mpgc.jtime119200.domain.Activity;
import it.unicam.cs.mpgc.jtime119200.domain.ActivityStatus;
import it.unicam.cs.mpgc.jtime119200.domain.service.ActivityTimeCalculator;
import it.unicam.cs.mpgc.jtime119200.domain.service.TimeServiceProvider;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ActivityViewModel extends TimeServiceProvider {

    private static final String zone = "Europe/Rome";
    private static final DateTimeFormatter hoursMins = DateTimeFormatter.ofPattern("HH:mm");
    private final Activity activity;
    private final ActivityTimeCalculator calculator;

    public ActivityViewModel(Activity activity) {
        this.activity = activity;
        calculator = new ActivityTimeCalculator(activity);
    }

    public String getProject(){
        return activity.getProject().toString();
    }

    public LocalDate getDate() {
        return activity.getDate();
    }

    public String getTooltipText() {
        return switch (activity.getStatus()) {
            case PLANNED -> "Start Time: " + getFormattedTime(activity.getStartTime()) +
                             "\nExpected End Time " + getFormattedTime(activity.expectedEndTime());

            case COMPLETED -> "Started at " + getFormattedTime(activity.getStartTime())+
                    "\nExpected End Time " + getFormattedTime(activity.expectedEndTime()) +
                    "\nActual EndTime: " + getFormattedTime(calculator.actualEndTime());

            case EXPIRED -> "Activity expired";
        };
    }
    public String getDescription(){
        return "Activity: " + activity.getTitle() +
                "\nProject: " + activity.getProject().toString();
    }

    public String getTitle(){
        return activity.getTitle();
    }

    public boolean isPlanned() {
        return activity.getStatus() == ActivityStatus.PLANNED;
    }

    public Activity getActivity() {
        return activity;
    }

    public String getProjectName() {
        return activity.getProject().getName();
    }

    public String getStartTime() {
        return getFormattedTime(activity.getStartTime());
    }

    public String getExpectedDurationMinutes() {
        return activity.getExpectedDuration().toMinutes()+"";
    }

    public String getStatusSymbol() {
        if (activity.getStatus().equals(ActivityStatus.PLANNED)) return "⏳";
        if (activity.getStatus().equals(ActivityStatus.COMPLETED)) return "✔";
        else return "✖";
    }

    public String getExpectedEnd() {
        return getFormattedTime(activity.expectedEndTime());
    }

    public String getActualEndOrDash() {
        if (activity.isCompleted()) return getFormattedTime(calculator.actualEndTime());
        else return "-";
    }

    public String getActualDurationOrDash() {
        if (activity.isCompleted()) return activity.getActualDuration().toMinutes()+"";
        else return "-";
    }

    public String getDurationDifference() {
        if (activity.isCompleted()) return calculator.estimationDifference().toMinutes()+"";
        else return "-";
    }
}
