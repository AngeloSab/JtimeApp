package it.unicam.cs.mpgc.jtime119200.domain.service;

import it.unicam.cs.mpgc.jtime119200.domain.Activity;
import it.unicam.cs.mpgc.jtime119200.domain.ActivityStatus;
import it.unicam.cs.mpgc.jtime119200.domain.Project;

public class ProjectProgressionCalculator {
    Project project;

    public ProjectProgressionCalculator(Project project) {
        this.project = project;
    }

    public boolean checkCompleted(){
        for (Activity activity : project.getActivities()){
            if (!activity.isCompleted()) return false;
        }
        return true;
    }

    public int getNumActivitiesCompleted() {
        int count = 0;
        for (Activity activity : project.getActivities()){
            if (activity.isCompleted()) count++;
        }
        return count;
    }

    public int getNumActivitiesExpired(){
        int count = 0;
        for (Activity activity : project.getActivities()){
            if (activity.getStatus() == ActivityStatus.EXPIRED) count++;
        }
        return count;
    }

    public double getProgression() {
        double count = 0;
        for (Activity activity : project.getActivities()) {
            if (activity.isCompleted()) count ++;
        }
        return count / (double) project.getActivities().size();
    }
}
