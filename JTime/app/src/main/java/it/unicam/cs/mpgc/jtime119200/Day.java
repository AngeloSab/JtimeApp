package it.unicam.cs.mpgc.jtime119200;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day {

    private final LocalDate date;
    private final List<Activity> activities = new ArrayList<>();

    public Day(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public List<Activity> getActivities() {
        return Collections.unmodifiableList(activities);
    }

    void addActivity(Activity activity) {
        activities.add(activity);
    }
    public void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    @Override
    public String toString() {
        return "Day{" + date + ", activities=" + activities.size() + "}";
    }

}
