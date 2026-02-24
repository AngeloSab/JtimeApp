package it.unicam.cs.mpgc.jtime119200.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a project that can contain multiple {@link Activity} instances.
 * A project is identified by its name and can track the activities associated with it.
 * Provides methods to add, remove, and query the status of its activities.
 */
public class Project {

    private final String name;
    private final List<Activity> activities = new ArrayList<>();

    /**
     * Constructs a new project with the given name.
     *
     * @param name the name of the project
     */
    public Project(String name) {
        this.name = name;
    }

    /**
     * Returns the name of the project.
     *
     * @return the project name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns an unmodifiable list of activities associated with this project.
     *
     * @return the list of activities
     */
    public List<Activity> getActivities() {
        return Collections.unmodifiableList(activities);
    }

    /**
     * Adds an activity to this project.
     *
     * @param activity the activity to add
     */
    void addActivity(Activity activity) {
        activities.add(activity);
    }

    /**
     * Removes an activity from this project.
     *
     * @param activity the activity to remove
     */
    public void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    /**
     * Returns true if all activities in the project are completed.
     *
     * @return true if all activities are completed, false otherwise
     */
    public boolean isCompleted() {
        for (Activity activity : activities) {
            if (!activity.isCompleted()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks equality based on the project name.
     *
     * @param obj the object to compare
     * @return true if the object is a Project with the same name
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Project other = (Project) obj;

        if (name == null) {
            return other.name == null;
        } else {
            return name.equals(other.name);
        }
    }

    /**
     * Returns the hash code based on the project name.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    /**
     * Returns a string representation of the project, which is its name.
     *
     * @return the project name as string
     */
    @Override
    public String toString() {
        return name;
    }
}