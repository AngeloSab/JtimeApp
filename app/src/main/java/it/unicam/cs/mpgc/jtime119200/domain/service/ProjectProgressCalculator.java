package it.unicam.cs.mpgc.jtime119200.domain.service;

import it.unicam.cs.mpgc.jtime119200.domain.Activity;
import it.unicam.cs.mpgc.jtime119200.domain.ActivityStatus;
import it.unicam.cs.mpgc.jtime119200.domain.Project;

public class ProjectProgressCalculator {
    Project project;

    public ProjectProgressCalculator(Project project) {
        this.project = project;
    }

    public int completedActivities(){
        int count = 0;
        for (Activity activity : project.getActivities()) {
            if (activity.isCompleted()) count++;
        }
        return count;
    }

    public int expiredActivities(){
        int count = 0;
        for (Activity activity : project.getActivities()) {
            if (activity.isExpired()) count++;
        }
        return count;
    }

    public int plannedActivities(){
        int count = 0;
        for (Activity activity : project.getActivities()) {
            if (activity.getStatus() == ActivityStatus.PLANNED) count++;
        }
        return count;
    }

    public float calculateProgression() {
        int totalActivities = project.getActivities().size();
        if (totalActivities == 0) {
            return 0f;
        }
        return (float) completedActivities() / totalActivities;
    }

    public float calculateExpiredPercentage(){
        int totalActivities = project.getActivities().size();
        if (totalActivities == 0) {
            return 0f;
        }
        return (float) expiredActivities() / totalActivities;
    }

    public int totalExpectedDuration() {
        int totalDuration = 0;
        for (Activity activity : project.getActivities()) {
            totalDuration += (int) activity.getExpectedDuration().toMinutes();
        }
        return totalDuration;
    }

    public int totalActualDuration() {
        int totalDuration = 0;
        for (Activity activity : project.getActivities()) {
            totalDuration += (int) activity.getActualDuration().toMinutes();
        }
        return totalDuration;
    }

}
