package it.unicam.cs.mpgc.jtime119200;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Project {

    private final String name;
    private boolean completed;
    private final List<Activity> activities = new ArrayList<>();

    public Project(String name) {
        this.name = Objects.requireNonNull(name);
        this.completed = false;
    }

    // ---------- getters ----------

    public String getName() {
        return name;
    }

    public boolean isCompleted() {
        return completed;
    }

    public List<Activity> getActivities() {
        return Collections.unmodifiableList(activities);
    }

    // ---------- behavior ----------

    void addActivity(Activity activity) {
        if (completed) {
            throw new IllegalStateException(
                    "Cannot add activity to a completed project");
        }
        activities.add(activity);
    }

    public boolean checkCompleted(){
        for (Activity activity : activities){
            if (!activity.isCompleted()) return false;
        }
        this.completed = true;
        return true;
    }

    public int getNumActivitiesCompleted() {
        int count = 0;
        for (Activity activity : activities){
            if (activity.isCompleted()) count++;
        }
        return count;
    }

    public int getNumActivitiesExpired(){
        int count = 0;
        for (Activity activity : activities){
            if (activity.getStatus() == ActivityStatus.EXPIRED) count++;
        }
    return count;
    }

    public double getProgression() {
        double count = 0;
        for (Activity activity : this.getActivities()) {
            if (activity.isCompleted()) count ++;
        }
        return count / (double) this.getActivities().size();
    }

    public void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    @Override
    public String toString() {
        return name;
    }
}
