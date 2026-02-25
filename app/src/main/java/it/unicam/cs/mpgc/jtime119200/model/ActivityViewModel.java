package it.unicam.cs.mpgc.jtime119200.model;

import it.unicam.cs.mpgc.jtime119200.domain.Activity;
import it.unicam.cs.mpgc.jtime119200.domain.ActivityStatus;
import it.unicam.cs.mpgc.jtime119200.domain.service.ActivityTimeCalculator;
import it.unicam.cs.mpgc.jtime119200.domain.service.TimeServiceProvider;

import java.time.LocalDate;

/**
 * ViewModel for a single activity. Provides formatted data and utility methods
 * to display activity information in the GUI.
 */
public class ActivityViewModel extends TimeServiceProvider {

    private final Activity activity;
    private final ActivityTimeCalculator calculator;

    /**
     * Constructs the view model for the given activity.
     *
     * @param activity the activity to wrap
     */
    public ActivityViewModel(Activity activity) {
        this.activity = activity;
        calculator = new ActivityTimeCalculator(activity);
    }

    /**
     * Returns the name of the project associated with this activity as a string.
     *
     * @return the project name
     */
    public String getProject(){
        return activity.getProject().toString();
    }

    /**
     * Returns the date of the activity.
     *
     * @return the activity date
     */
    public LocalDate getDate() {
        return activity.getDate();
    }

    /**
     * Returns a tooltip text describing the activity, including start time,
     * expected end, and actual end if completed.
     *
     * @return the tooltip text
     */
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

    /**
     * Returns a string description of the activity including title and project.
     *
     * @return activity description
     */
    public String getDescription(){
        return "Activity: " + activity.getTitle() +
                "\nProject: " + activity.getProject().toString();
    }

    /**
     * Returns the title of the activity.
     *
     * @return activity title
     */
    public String getTitle(){
        return activity.getTitle();
    }

    /**
     * Returns true if the activity is planned.
     *
     * @return true if planned, false otherwise
     */
    public boolean isPlanned() {
        return activity.getStatus() == ActivityStatus.PLANNED;
    }

    /**
     * Returns the wrapped activity.
     *
     * @return the activity
     */
    public Activity getActivity() {
        return activity;
    }

    /**
     * Returns the name of the project this activity belongs to.
     *
     * @return project name
     */
    public String getProjectName() {
        return activity.getProject().getName();
    }

    /**
     * Returns the start time of the activity formatted as HH:mm.
     *
     * @return formatted start time
     */
    public String getStartTime() {
        return getFormattedTime(activity.getStartTime());
    }

    /**
     * Returns the expected duration of the activity in minutes as a string.
     *
     * @return expected duration in minutes
     */
    public String getExpectedDurationMinutes() {
        return activity.getExpectedDuration().toMinutes()+"";
    }

    /**
     * Returns a symbol representing the activity status.
     * ⏳ = planned, ✔ = completed, ✖ = expired.
     *
     * @return status symbol
     */
    public String getStatusSymbol() {
        if (activity.getStatus().equals(ActivityStatus.PLANNED)) return "⏳";
        if (activity.getStatus().equals(ActivityStatus.COMPLETED)) return "✔";
        else return "✖";
    }

    /**
     * Returns the expected end time formatted as HH:mm.
     *
     * @return formatted expected end time
     */
    public String getExpectedEnd() {
        return getFormattedTime(activity.expectedEndTime());
    }

    /**
     * Returns the actual end time formatted as HH:mm, or "-" if not completed.
     *
     * @return formatted actual end time or "-"
     */
    public String getActualEndOrDash() {
        if (activity.isCompleted()) return getFormattedTime(calculator.actualEndTime());
        else return "-";
    }

    /**
     * Returns the actual duration in minutes as string, or "-" if not completed.
     *
     * @return actual duration or "-"
     */
    public String getActualDurationOrDash() {
        if (activity.isCompleted()) return activity.getActualDuration().toMinutes()+"";
        else return "-";
    }

    /**
     * Returns the difference between actual and expected duration in minutes as string,
     * or "-" if the activity is not completed.
     *
     * @return duration difference or "-"
     */
    public String getDurationDifference() {
        if (activity.isCompleted()) return calculator.estimationDifference().toMinutes()+"";
        else return "-";
    }
}