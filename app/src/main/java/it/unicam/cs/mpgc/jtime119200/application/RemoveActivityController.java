package it.unicam.cs.mpgc.jtime119200.application;

import it.unicam.cs.mpgc.jtime119200.domain.JtimeCalendar;
import it.unicam.cs.mpgc.jtime119200.gui.form.RemoveActivityForm;
import it.unicam.cs.mpgc.jtime119200.model.ActivityViewModel;
import it.unicam.cs.mpgc.jtime119200.model.WeeklyViewModel;

public class RemoveActivityController {

    private final WeeklyViewModel weeklyViewModel;
    private final ActivityViewModel activityViewModel;
    private final JtimeCalendar calendar;

    public RemoveActivityController(WeeklyViewModel weeklyViewModel, ActivityViewModel activity) {
        this.weeklyViewModel = weeklyViewModel;
        this.activityViewModel = activity;
        this.calendar = weeklyViewModel.getCalendar();
    }

    public void removeActivity(ActivityViewModel activityViewModel) {
        calendar.removeActivity(activityViewModel.getActivity().getProject(), activityViewModel.getActivity());
        weeklyViewModel.notifyListeners();
    }

    public void removeActivitySignal() {
        new RemoveActivityForm(this, activityViewModel);
    }

}
