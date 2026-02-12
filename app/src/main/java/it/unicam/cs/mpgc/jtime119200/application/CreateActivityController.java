package it.unicam.cs.mpgc.jtime119200.application;

import it.unicam.cs.mpgc.jtime119200.domain.JtimeCalendar;
import it.unicam.cs.mpgc.jtime119200.gui.form.CreateAndEditActivityForm;
import it.unicam.cs.mpgc.jtime119200.model.form.CreateAndEditActivityFormModel;
import it.unicam.cs.mpgc.jtime119200.model.WeeklyViewModel;
import javafx.stage.Stage;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;

public class CreateActivityController {

    private final WeeklyViewModel weeklyViewModel;
    private final LocalDate day;
    private final JtimeCalendar calendar;

    public CreateActivityController(WeeklyViewModel weeklyViewModel, LocalDate day) {
        this.weeklyViewModel = weeklyViewModel;
        this.day = day;
        this.calendar = weeklyViewModel.getCalendar();
    }

    public void createActivitySignal() {
        CreateAndEditActivityFormModel afm = new CreateAndEditActivityFormModel(weeklyViewModel, day);
        new CreateAndEditActivityForm(afm, day);
    }

    public void createActivity(String projectName, String title, Duration expectedDuration, Instant startTime, LocalDate day) {
        calendar.createActivity(calendar.createProject(projectName), title, expectedDuration, startTime, day);
        weeklyViewModel.notifyListeners();
    }
}


