package it.unicam.cs.mpgc.jtime119200.controllers;

import it.unicam.cs.mpgc.jtime119200.gui.ActivityView;
import it.unicam.cs.mpgc.jtime119200.model.ActivityViewModel;
import it.unicam.cs.mpgc.jtime119200.model.WeeklyViewModel;

import java.time.LocalDate;

public class EventController {

    private final WeeklyViewModel weeklyViewModel;
    private ActivityView selectedActivityView;

    public EventController(WeeklyViewModel weeklyViewModel) {
        this.weeklyViewModel = weeklyViewModel;
    }
    public void onPrevWeek() {
        weeklyViewModel.changeWeek(-1);
    }

    public void onNextWeek() {
        weeklyViewModel.changeWeek(1);
    }

    public void onDayClicked(LocalDate date) {
        CreateActivityController controller = new CreateActivityController(weeklyViewModel, date);
        controller.createActivitySignal();
    }

    public void onEdit(ActivityViewModel vm) {
        EditActivityController controller = new EditActivityController(weeklyViewModel, vm);
        controller.editActivitySignal();
    }

    public void onRemove(ActivityViewModel vm) {
        RemoveActivityController controller = new RemoveActivityController(weeklyViewModel, vm);
        controller.removeActivitySignal();
    }

    public void onSelect(ActivityView view) {

        if (selectedActivityView != null &&
                selectedActivityView != view) {

            selectedActivityView.hideCheckButton();
        }

        selectedActivityView = view;

        CompleteActivityController controller =
                new CompleteActivityController(view);

        controller.selectSignal();
    }

    public void onComplete(ActivityView view) {

        CompleteActivityController controller =
                new CompleteActivityController(view);

        controller.completeSignal();
    }
}
