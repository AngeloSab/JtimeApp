package it.unicam.cs.mpgc.jtime119200.model;

import it.unicam.cs.mpgc.jtime119200.domain.Day;
import it.unicam.cs.mpgc.jtime119200.domain.JtimeCalendar;
import it.unicam.cs.mpgc.jtime119200.domain.service.WeekPeriodConstructor;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * View model for a weekly calendar view.
 */
public class WeeklyViewModel {

    private final JtimeCalendar calendar;
    private int weekOffset;
    List<DailyViewModel> days;

    private final List<Runnable> listeners = new ArrayList<>();


    public WeeklyViewModel(JtimeCalendar calendar, int weekOffset) {
        this.calendar = Objects.requireNonNull(calendar);
        this.weekOffset = weekOffset;
        days = new ArrayList<>();
        buildWeeklyView();
    }

    public void buildWeeklyView() {
        days = new ArrayList<>();
        WeekPeriodConstructor wpc = new WeekPeriodConstructor(weekOffset);
        for (LocalDate date : wpc.getDays()) {
            Day day = calendar.getDay(date);
            List<ActivityViewModel> activityVMs = day.getActivities().stream()
                    .sorted()
                    .map(ActivityViewModel::new)
                    .toList();
            days.add(new DailyViewModel(date, activityVMs));
        }
    }

    public void reloadWeek() {
        buildWeeklyView();
        notifyListeners();
    }

    public void changeWeek(int offset) {
        weekOffset += offset;
        this.reloadWeek();
    }

    public String prevButtonString(){
        return "Previous week";
    }

    public String nextButtonString(){
        return "Next week";
    }

    public void addListener(Runnable listener) {
        listeners.add(listener);
    }

    public void notifyListeners() {
        buildWeeklyView();
        listeners.forEach(Runnable::run);
    }

    public JtimeCalendar getCalendar() {
        return calendar;
    }

    public LocalDate getWeekStart() {
        return days.getFirst().getDate();
    }

    public LocalDate getWeekEnd() {
        return days.getLast().getDate();
    }

    public List<DailyViewModel> getDays() {
        return this.days;
    }

    public String reportString(){
        return "Select the Project's Report";
    }

    public String getWeekLabel() {
        return "Week " + getWeekStart() + " - " + getWeekEnd();
    }
}
