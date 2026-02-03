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

    public void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    public void complete() {
        completed = true;
    }

    @Override
    public String toString() {
        return name;
    }

}
