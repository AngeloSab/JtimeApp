package it.unicam.cs.mpgc.jtime119200.model;

import it.unicam.cs.mpgc.jtime119200.domain.Day;
import it.unicam.cs.mpgc.jtime119200.domain.JtimeCalendar;
import it.unicam.cs.mpgc.jtime119200.domain.service.WeekPeriodConstructor;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * ViewModel representing a weekly view of the calendar.
 * Holds the list of daily views and provides methods to navigate weeks.
 */
public class WeeklyViewModel {

    private final JtimeCalendar calendar;
    private int weekOffset;
    List<DailyViewModel> days;

    private final List<Runnable> listeners = new ArrayList<>();

    /**
     * Constructs a WeeklyViewModel for a given calendar and week offset.
     *
     * @param calendar the calendar to retrieve data from
     * @param weekOffset the number of weeks offset from the current week
     */
    public WeeklyViewModel(JtimeCalendar calendar, int weekOffset) {
        this.calendar = Objects.requireNonNull(calendar);
        this.weekOffset = weekOffset;
        days = new ArrayList<>();
        buildWeeklyView();
    }

    /**
     * Builds the list of DailyViewModel objects for the current week.
     */
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

    /**
     * Reloads the current week and notifies all registered listeners.
     */
    public void reloadWeek() {
        buildWeeklyView();
        notifyListeners();
    }

    /**
     * Changes the current week by the given offset.
     *
     * @param offset number of weeks to move forward or backward
     */
    public void changeWeek(int offset) {
        weekOffset += offset;
        this.reloadWeek();
    }

    /**
     * Returns the label for the "previous week" button.
     *
     * @return label string
     */
    public String prevButtonString(){
        return "Previous week";
    }

    /**
     * Returns the label for the "next week" button.
     *
     * @return label string
     */
    public String nextButtonString(){
        return "Next week";
    }

    /**
     * Adds a listener to be notified when the weekly view changes.
     *
     * @param listener a Runnable to execute on updates
     */
    public void addListener(Runnable listener) {
        listeners.add(listener);
    }

    /**
     * Notifies all registered listeners and rebuilds the weekly view.
     */
    public void notifyListeners() {
        buildWeeklyView();
        listeners.forEach(Runnable::run);
    }

    /**
     * Returns the underlying calendar.
     *
     * @return JtimeCalendar object
     */
    public JtimeCalendar getCalendar() {
        return calendar;
    }

    /**
     * Returns the date of the first day of the current week.
     *
     * @return LocalDate of the week's start
     */
    public LocalDate getWeekStart() {
        return days.getFirst().getDate();
    }

    /**
     * Returns the date of the last day of the current week.
     *
     * @return LocalDate of the week's end
     */
    public LocalDate getWeekEnd() {
        return days.getLast().getDate();
    }

    /**
     * Returns the list of daily view models for the week.
     *
     * @return list of DailyViewModel objects
     */
    public List<DailyViewModel> getDays() {
        return this.days;
    }

    /**
     * Returns the label for the report selection box.
     *
     * @return report selection string
     */
    public String reportString(){
        return "Select the Project's Report";
    }

    /**
     * Returns a label representing the current week range.
     *
     * @return formatted week label
     */
    public String getWeekLabel() {
        return "Week " + getWeekStart() + " - " + getWeekEnd();
    }
}