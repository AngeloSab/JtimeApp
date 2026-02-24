package it.unicam.cs.mpgc.jtime119200.model;

import it.unicam.cs.mpgc.jtime119200.domain.Activity;
import it.unicam.cs.mpgc.jtime119200.domain.Project;
import it.unicam.cs.mpgc.jtime119200.domain.service.ProjectProgressCalculator;

import java.util.List;

public class ProjectReportViewModel {

    private final Project project;
    private final ProjectProgressCalculator progressCalculator;

    public ProjectReportViewModel(Project project) {
        this.project = project;
        this.progressCalculator = new ProjectProgressCalculator(project);
    }

    public String getTitle() {
        return "Project "+ project.getName() + "'s Report";
    }
    public String projectName() {
        return project.getName();
    }
    public String progressBarTitle() {
        return "Project Progression: "+String.format("%.2f", progressBarValue()*100)+" %";
    }
    public float progressBarValue() { return progressCalculator.calculateProgression();}


    /**
     * Returns the status message of the project based on its completion state.
     *
     * @return a String describing the project status
     */
    public String projectStatus() {
        if (project.isCompleted()) {
            return "Status Finished: all activities are completed";
        } else {
            return "Status Uncompleted: One or more activities are not completed.";
        }
    }

    public boolean isCompleted(){
        return project.isCompleted();
    }

    public double getCompleted() {
        return progressCalculator.completedActivities();
    }

    public double getPlanned() {
        return progressCalculator.plannedActivities();
    }

    public double getExpired() {
        return progressCalculator.expiredActivities();
    }

    public List<Activity> getActivities() {
        return project.getActivities();
    }

    public double getTotalExpected() {
        double total = 0;
        for (Activity activity : project.getActivities()) {
            total += activity.getExpectedDuration().toMinutes();
        }
        return total;
    }

    public double getTotalActual() {
        double total = 0;
        for (Activity activity : project.getActivities()) {
            if (activity.isCompleted()) {
                total += activity.getActualDuration().toMinutes();
            }
        }
        return total;
    }


    public double getTotalDifference() {
        return getTotalActual() - getTotalExpected();
    }


    public double getAverageExpected() {
        if (project.getActivities().isEmpty()) return 0;
        return getTotalExpected() / project.getActivities().size();
    }


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

    public double getAverageDifference() {
        return getAverageActual() - getAverageExpected();
    }

    public String activityListLabel() {
        return "Activity List: ";
    }
}
