package it.unicam.cs.mpgc.jtime119200.controllers;

import it.unicam.cs.mpgc.jtime119200.domain.Project;
import it.unicam.cs.mpgc.jtime119200.gui.ActivityView;
import it.unicam.cs.mpgc.jtime119200.gui.ProjectReportView;
import it.unicam.cs.mpgc.jtime119200.model.ActivityViewModel;
import it.unicam.cs.mpgc.jtime119200.model.WeeklyViewModel;

import java.time.LocalDate;
import java.util.List;

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
        if (selectedActivityView != null && selectedActivityView != view)
            selectedActivityView.hideCheckButton();

        selectedActivityView = view;
        view.showCheckButton();
    }

    public void clearSelection() {
        if (selectedActivityView != null) {
            selectedActivityView.hideCheckButton();
            selectedActivityView = null;
        }
    }

    public void onComplete(ActivityView view) {
        CompleteActivityController controller = new CompleteActivityController(view, weeklyViewModel);
        controller.completeSignal();
    }

    public List<Project> getProjectList() {
        return weeklyViewModel.getCalendar().getProjects();
    }

    public void projectReport(Project project) {
        ReportController reportController = new ReportController(weeklyViewModel, project);
        new ProjectReportView(reportController);
    }
}
