package it.unicam.cs.mpgc.jtime119200.controllers;

import it.unicam.cs.mpgc.jtime119200.domain.JtimeCalendar;
import it.unicam.cs.mpgc.jtime119200.gui.form.CreateAndEditActivityForm;
import it.unicam.cs.mpgc.jtime119200.model.form.CreateAndEditActivityFormModel;
import it.unicam.cs.mpgc.jtime119200.model.ActivityViewModel;
import it.unicam.cs.mpgc.jtime119200.model.WeeklyViewModel;

import java.time.Duration;
import java.time.Instant;

public class EditActivityController {

    private final WeeklyViewModel weeklyViewModel;
    private final ActivityViewModel activityViewModel;
    private final JtimeCalendar calendar;

    public EditActivityController(WeeklyViewModel weeklyViewModel, ActivityViewModel activity) {
        this.weeklyViewModel = weeklyViewModel;
        this.activityViewModel = activity;
        this.calendar = weeklyViewModel.getCalendar();
    }

    public void editActivity(String project, Duration expectedDuration, Instant startTime) {
        calendar.editActivity(activityViewModel.getActivity(), calendar.createProject(project), expectedDuration, startTime);
        weeklyViewModel.notifyListeners();
    }

    public void editActivitySignal() {
        CreateAndEditActivityFormModel afm = new CreateAndEditActivityFormModel(weeklyViewModel, activityViewModel, activityViewModel.getDate());
        new CreateAndEditActivityForm(afm, activityViewModel.getDate());
    }
}
