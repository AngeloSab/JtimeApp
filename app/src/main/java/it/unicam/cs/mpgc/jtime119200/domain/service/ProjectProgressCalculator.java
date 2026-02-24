package it.unicam.cs.mpgc.jtime119200.domain.service;

import it.unicam.cs.mpgc.jtime119200.domain.Activity;
import it.unicam.cs.mpgc.jtime119200.domain.ActivityStatus;
import it.unicam.cs.mpgc.jtime119200.domain.Project;

/**
 * Service class responsible for calculating progress-related metrics of a {@link Project}.
 * This class aggregates information about the activities contained in a project
 * and provides methods to determine how many activities are completed, expired,
 * planned, and the overall progression ratio of the project.
 * It encapsulates progress calculation logic, keeping the {@link Project}
 * entity focused on representing domain state rather than computing statistics.
 */
public class ProjectProgressCalculator {
    private final Project project;

    /**
     * Creates a new calculator for the specified project.
     *
     * @param project the project whose progress will be analyzed
     */
    public ProjectProgressCalculator(Project project) {
        this.project = project;
    }

    /**
     * Counts the number of completed activities in the project.
     *
     * @return the number of activities marked as completed
     */
    public int completedActivities(){
        int count = 0;
        for (Activity activity : project.getActivities()) {
            if (activity.isCompleted()) count++;
        }
        return count;
    }

    /**
     * Counts the number of expired activities in the project.
     *
     * @return the number of activities marked as expired
     */
    public int expiredActivities(){
        int count = 0;
        for (Activity activity : project.getActivities()) {
            if (activity.isExpired()) count++;
        }
        return count;
    }

    /**
     * Counts the number of planned activities in the project.
     *
     * @return the number of activities currently in PLANNED status
     */
    public int plannedActivities(){
        int count = 0;
        for (Activity activity : project.getActivities()) {
            if (activity.getStatus() == ActivityStatus.PLANNED) count++;
        }
        return count;
    }

    /**
     * Calculates the progression ratio of the project.
     * The progression is defined as the number of completed activities
     * divided by the total number of activities in the project.
     * If the project contains no activities, the progression is defined as 0.
     *
     * @return a float value between 0 and 1 representing the completion ratio
     */
    public float calculateProgression() {
        int totalActivities = project.getActivities().size();
        if (totalActivities == 0) {
            return 0f;
        }
        return (float) completedActivities() / totalActivities;
    }
}