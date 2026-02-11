package it.unicam.cs.mpgc.jtime119200.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

/**
 * View model representing a single day and its activities.
 */
public class DailyViewModel {

    private final LocalDate date;
    private final List<ActivityViewModel> activities;

    public DailyViewModel(LocalDate date, List<ActivityViewModel> activities) {
        this.date = Objects.requireNonNull(date);
        this.activities = Objects.requireNonNull(activities);
    }

    public LocalDate getDate() {
        return date;
    }

    public List<ActivityViewModel> getActivities() {
        return activities;
    }

    public String getDateLabel() {
        return date.getDayOfWeek().name().substring(0, 3) +" "+ date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))+" ";
    }
}