package it.unicam.cs.mpgc.jtime119200;

public class ProjectProgressionCalculator {
    Project project;

    public ProjectProgressionCalculator(Project project) {
        this.project = project;
    }

    public boolean checkCompleted(){
        for (Activity activity : project.getActivities()){
            if (!activity.isCompleted()) return false;
        }
        project.complete();
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
