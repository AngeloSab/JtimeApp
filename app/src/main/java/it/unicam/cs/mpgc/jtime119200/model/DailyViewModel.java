package it.unicam.cs.mpgc.jtime119200.model;

import java.time.LocalDate;
import java.util.List;

public class DailyViewModel {

    private final LocalDate date;
    private final List<ActivityViewModel> activities;

    public DailyViewModel(LocalDate date, List<ActivityViewModel> activities) {
        this.date = date;
        this.activities = activities;
    }

    public LocalDate getDate() {
        return date;
    }

    public List<ActivityViewModel> getActivities() {
        return activities;
    }

    public String getDateLabel() {
        return date.toString();
    }
}

