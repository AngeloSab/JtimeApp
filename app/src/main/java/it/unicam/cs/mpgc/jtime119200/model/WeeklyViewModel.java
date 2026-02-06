package it.unicam.cs.mpgc.jtime119200.model;

import it.unicam.cs.mpgc.jtime119200.domain.Day;
import it.unicam.cs.mpgc.jtime119200.domain.JtimeCalendar;
import it.unicam.cs.mpgc.jtime119200.domain.service.ActivityTimeCalculator;
import it.unicam.cs.mpgc.jtime119200.domain.service.WeekPeriodConstructor;
import javafx.scene.control.Label;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class WeeklyViewModel {

    private  final int offset;
    private final List<DailyViewModel> days = new ArrayList<>();

    public WeeklyViewModel(JtimeCalendar calendar, int offset) {
        this.offset = offset;
        WeekPeriodConstructor wpc = new WeekPeriodConstructor(offset);
        for (LocalDate date : wpc.getDays()) {
            Day day = calendar.getDay(date);
            List<ActivityViewModel> activityVMs = day.getActivities().stream().sorted()
                    .map(a -> new ActivityViewModel(a, new ActivityTimeCalculator(a))).toList();
            days.add(new DailyViewModel(date, activityVMs));
        }
    }

    public WeeklyViewModel nextWeek(JtimeCalendar calendar) {
        return new WeeklyViewModel(calendar, this.offset + 1);
    }

    public WeeklyViewModel previousWeek(JtimeCalendar calendar) {
        return new WeeklyViewModel(calendar, this.offset - 1);
    }

    public List<DailyViewModel> getDays() {
        return days;
    }

    public String getWeekLabel(){
        return "Week: "+ days.getFirst().getDate().toString() +" - "+days.getLast().getDate().toString();
    }



}
