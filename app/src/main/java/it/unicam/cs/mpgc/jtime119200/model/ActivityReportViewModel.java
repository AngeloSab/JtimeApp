package it.unicam.cs.mpgc.jtime119200.model;

import it.unicam.cs.mpgc.jtime119200.domain.Activity;
import it.unicam.cs.mpgc.jtime119200.domain.service.ActivityTimeCalculator;
import it.unicam.cs.mpgc.jtime119200.domain.service.ProjectProgressionCalculator;

public class ActivityReportViewModel {

    private final Activity activity;
    private final ActivityTimeCalculator timeCalculator;
    private final ProjectProgressionCalculator projectCalculator;

    public ActivityReportViewModel(Activity activity) {
        this.activity = activity;
        this.timeCalculator = new ActivityTimeCalculator(activity);
        this.projectCalculator =
                new ProjectProgressionCalculator(activity.getProject());
    }

    public String getActivityTitle() {
        return activity.getTitle();
    }

    public String getProjectName() {
        return activity.getProject().getName();
    }

    public long getExpectedDurationMinutes() {
        return activity.getExpectedDuration().toMinutes();
    }

    public long getActualDurationMinutes() {
        return activity.getActualDuration().toMinutes();
    }

    public long getEstimationDifferenceMinutes() {
        return timeCalculator.estimationDifference().toMinutes();
    }

    public double getProjectProgress() {
        return projectCalculator.getProgression();
    }

    public boolean isProjectCompleted() {
        return projectCalculator.checkCompleted();
    }
}

