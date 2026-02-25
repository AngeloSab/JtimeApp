package it.unicam.cs.mpgc.jtime119200.model;

import it.unicam.cs.mpgc.jtime119200.domain.Activity;
import it.unicam.cs.mpgc.jtime119200.domain.Project;
import it.unicam.cs.mpgc.jtime119200.domain.service.ProjectProgressCalculator;

import java.util.List;

/**
 * ViewModel representing the report of a project.
 * Provides summary statistics, progress values, and activity information for display in the UI.
 */
public class ProjectReportViewModel {

    private final Project project;
    private final ProjectProgressCalculator progressCalculator;

    /**
     * Constructs a ProjectReportViewModel for the given project.
     *
     * @param project the project to create a report for
     */
    public ProjectReportViewModel(Project project) {
        this.project = project;
        this.progressCalculator = new ProjectProgressCalculator(project);
    }

    /**
     * Returns the title for the report.
     *
     * @return formatted title string
     */
    public String getTitle() {
        return "Project "+ project.getName() + "'s Report";
    }

    /**
     * Returns the name of the project.
     *
     * @return project name string
     */
    public String projectName() {
        return project.getName();
    }

    /**
     * Returns the label text for the progress bar, including the percentage.
     *
     * @return formatted progress bar title
     */
    public String progressBarTitle() {
        return "Project Progression: "+String.format("%.2f", progressBarValue()*100)+" %";
    }

    /**
     * Returns the column headers for the activity table in the report.
     *
     * @return array of header strings
     */
    public String[] headersStrings() {
        return new String[] {
                "Date",
                "Name",
                "Status",
                "Start Time",
                "Expected End",
                "Actual End",
                "Expected Dur.",
                "Actual Dur.",
                "Dur. Difference"
        };
    }

    /**
     * Returns the progress of the project as a float value between 0 and 1.
     *
     * @return progress value
     */
    public float progressBarValue() { return progressCalculator.calculateProgression();}

    /**
     * Returns the status message of the project based on its completion state.
     *
     * @return string describing the project status
     */
    public String projectStatus() {
        if (project.isCompleted()) {
            return "Status Finished: all activities are completed";
        } else {
            return "Status Uncompleted: One or more activities are not completed.";
        }
    }

    /**
     * Returns whether the project is fully completed.
     *
     * @return true if all activities are completed, false otherwise
     */
    public boolean isCompleted(){
        return project.isCompleted();
    }

    /**
     * Returns the number of completed activities in the project.
     *
     * @return number of completed activities
     */
    public double getCompleted() {
        return progressCalculator.completedActivities();
    }

    /**
     * Returns the number of planned activities in the project.
     *
     * @return number of planned activities
     */
    public double getPlanned() {
        return progressCalculator.plannedActivities();
    }

    /**
     * Returns the number of expired activities in the project.
     *
     * @return number of expired activities
     */
    public double getExpired() {
        return progressCalculator.expiredActivities();
    }

    /**
     * Returns the list of all activities in the project.
     *
     * @return list of Activity objects
     */
    public List<Activity> getActivities() {
        return project.getActivities();
    }

    /**
     * Returns the total expected duration of all activities in minutes.
     *
     * @return total expected duration
     */
    public double getTotalExpected() {
        double total = 0;
        for (Activity activity : project.getActivities()) {
            total += activity.getExpectedDuration().toMinutes();
        }
        return total;
    }

    /**
     * Returns the total actual duration of all completed activities in minutes.
     *
     * @return total actual duration
     */
    public double getTotalActual() {
        double total = 0;
        for (Activity activity : project.getActivities()) {
            if (activity.isCompleted()) {
                total += activity.getActualDuration().toMinutes();
            }
        }
        return total;
    }

    /**
     * Returns the difference between total actual duration and total expected duration.
     *
     * @return total difference in minutes
     */
    public double getTotalDifference() {
        return getTotalActual() - getTotalExpected();
    }

    /**
     * Returns the average expected duration of activities in minutes.
     *
     * @return average expected duration
     */
    public double getAverageExpected() {
        if (project.getActivities().isEmpty()) return 0;
        return getTotalExpected() / project.getActivities().size();
    }

    /**
     * Returns the average actual duration of completed activities in minutes.
     *
     * @return average actual duration
     */
    public double getAverageActual() {
        double total = 0;
        int count = 0;
        for (Activity activity : project.getActivities()) {
            if (activity.isCompleted()) {
                total += activity.getActualDuration().toMinutes();
                count++;
            }
        }
        if (count == 0) return 0;
        return total / count;
    }

    /**
     * Returns the average difference between actual and expected durations.
     *
     * @return average difference in minutes
     */
    public double getAverageDifference() {
        return getAverageActual() - getAverageExpected();
    }

    /**
     * Returns the label for the activity list section in the report.
     *
     * @return activity list label string
     */
    public String activityListLabel() {
        return "Activity List: ";
    }
}