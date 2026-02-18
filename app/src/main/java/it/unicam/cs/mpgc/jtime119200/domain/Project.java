package it.unicam.cs.mpgc.jtime119200.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Project {

    private final String name;
    private final List<Activity> activities = new ArrayList<>();

    public Project(String name) {
        this.name = name;
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
        activities.add(activity);
    }

    public void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    public boolean isCompleted() {
        for (Activity activity : activities) {
            if (!activity.isCompleted()) {
                return false;
            }
        }
        return true;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false; // null o classi diverse

        Project other = (Project) obj;

        if (name == null) {
            return other.name == null;
        } else {
            return name.equals(other.name);
        }
    }

    @Override
    public int hashCode() {
        // Usa hashCode di projectName o 0 se null
        return name != null ? name.hashCode() : 0;
    }


    @Override
    public String toString() {
        return name;
    }
}
