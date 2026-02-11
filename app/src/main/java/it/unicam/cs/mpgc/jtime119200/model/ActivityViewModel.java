package it.unicam.cs.mpgc.jtime119200.model;

import it.unicam.cs.mpgc.jtime119200.domain.Activity;
import it.unicam.cs.mpgc.jtime119200.domain.ActivityStatus;
import it.unicam.cs.mpgc.jtime119200.domain.service.ActivityTimeCalculator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class ActivityViewModel {

    private static final String zone = "Europe/Rome";
    private static final DateTimeFormatter hoursMins = DateTimeFormatter.ofPattern("HH:mm");
    private final Activity activity;
    ActivityTimeCalculator calculator;

    public ActivityViewModel(Activity activity) {
        this.activity = activity;
        calculator = new ActivityTimeCalculator(activity);
    }

    public String getTitle() {
        return activity.getTitle();
    }

    public String getProject(){
        return activity.getProject().toString();
    }

    public LocalDate getDate() {
        return activity.getDate();
    }

    public String getTooltipText() {
        return switch (activity.getStatus()) {
            case PLANNED -> "Start Time: " + activity.getStartTime().atZone(ZoneId.of(zone)).format(hoursMins) +
                             "\nExpected End Time " + activity.expectedEndTime().atZone(ZoneId.of(zone)).format(hoursMins);
            case COMPLETED -> "Started at " + activity.getStartTime().atZone(ZoneId.of(zone)).format(hoursMins)+
                    "\nExpected End Time " + activity.expectedEndTime().atZone(ZoneId.of(zone)).format(hoursMins) +
                    "\nActual Duration: " + activity.getActualDuration().toMinutes() + " min" +
                    "\nEstimation Difference: " + calculator.estimationDifference().toMinutes() + " min";
            case EXPIRED -> "Activity expired";
        };
    }

    public String getBorderStyle() {
        return switch (activity.getStatus()) {
            case PLANNED -> "-fx-border-color: blue;";
            case COMPLETED -> "-fx-border-color: red;";
            case EXPIRED -> "-fx-border-color: gray;";
        };
    }

    public boolean isClickable() {
        return activity.getStatus() != ActivityStatus.COMPLETED;
    }

    public Activity getActivity() {
        return activity;
    }

    public String getProjectName() {
        return activity.getProject().getName();
    }

    public String getStartTimeAsString() {
        return activity.getStartTime().atZone(ZoneId.of(zone)).format(hoursMins);
    }

    public String getExpectedDurationMinutes() {
        return activity.getExpectedDuration().toMinutes()+"";
    }
}
