package it.unicam.cs.mpgc.jtime119200.model;

import it.unicam.cs.mpgc.jtime119200.domain.Activity;
import it.unicam.cs.mpgc.jtime119200.domain.ActivityStatus;
import it.unicam.cs.mpgc.jtime119200.domain.service.ActivityTimeCalculator;
import java.time.format.DateTimeFormatter;

public class ActivityViewModel {

    private final Activity activity;
    private final ActivityTimeCalculator calculator;

    public ActivityViewModel(Activity activity, ActivityTimeCalculator calculator) {
        this.activity = activity;
        this.calculator = calculator;
    }

    public String getTitle() {
        return activity.getTitle();
    }

    public String getTimeLabel() {
        return activity.getStartTime()
                .atZone(java.time.ZoneId.of("UTC+1"))
                .format(DateTimeFormatter.ofPattern("HH:mm"))
                + " - " +
                activity.expectedEndTime()
                        .atZone(java.time.ZoneId.of("UTC+1"))
                        .format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public String getTooltipText() {
        return switch (activity.getStatus()) {
            case PLANNED -> "Start: " + getTimeLabel();
            case COMPLETED -> "Started at " + getTimeLabel() +
                    "\nActual Duration: " + activity.getActualDuration().toMinutes() + " min" +
                    "\nEstimation Difference: " + calculator.estimationDifference().toMinutes() + " min";
            case EXPIRED -> "Activity expired";
        };
    }

    public String getBorderStyle() {
        return switch (activity.getStatus()) {
            case PLANNED -> "-fx-border-color: black;";
            case COMPLETED -> "-fx-border-color: red;";
            case EXPIRED -> "-fx-border-color: gray;";
        };
    }

    public boolean isClickable() {
        return activity.getStatus() != ActivityStatus.COMPLETED;
    }

    public boolean isCompleted() {
        return activity.isCompleted();
    }

    public Activity getActivity() {
        return activity;
    }
}

