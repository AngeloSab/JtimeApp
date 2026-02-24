package it.unicam.cs.mpgc.jtime119200.controllers;

import it.unicam.cs.mpgc.jtime119200.controllers.activityHandler.CreateActivityController;
import it.unicam.cs.mpgc.jtime119200.controllers.activityHandler.EditActivityController;
import it.unicam.cs.mpgc.jtime119200.controllers.activityHandler.RemoveActivityController;
import it.unicam.cs.mpgc.jtime119200.controllers.completeHandler.CompleteActivityController;
import it.unicam.cs.mpgc.jtime119200.controllers.reportHandler.ReportController;
import it.unicam.cs.mpgc.jtime119200.domain.Project;
import it.unicam.cs.mpgc.jtime119200.gui.ActivityView;
import it.unicam.cs.mpgc.jtime119200.model.ActivityViewModel;
import it.unicam.cs.mpgc.jtime119200.model.WeeklyViewModel;

import java.time.LocalDate;
import java.util.List;

/**
 * Facade controller for the weekly event management subsystem.
 * This class provides a unified and simplified interface between the
 * graphical user interface and the set of specialized controllers
 * responsible for handling activities and reports.
 * EventController does not implement the business logic directly.
 * Instead, it coordinates user interactions and delegates operations
 * to dedicated controllers such as:
 *   CreateActivityController
 *   EditActivityController
 *   RemoveActivityController
 *   CompleteActivityController
 *   ReportController
 * By centralizing access to these components, this class hides the
 * internal structure of the activity-handling subsystem from the view
 * layer. The UI interacts only with EventController, without needing
 * to know which specific controller performs each operation.
 * This design follows the Facade pattern, promoting separation of
 * concerns, reducing coupling between view and controllers, and
 * improving maintainability.
 */
public class EventController {

    private final WeeklyViewModel weeklyViewModel;
    private ActivityView selectedActivityView;

    /**
     * Creates a new EventController bound to the given WeeklyViewModel.
     *
     * @param weeklyViewModel the view model associated with the weekly view
     */
    public EventController(WeeklyViewModel weeklyViewModel) {
        this.weeklyViewModel = weeklyViewModel;
    }

    /**
     * Handles navigation to the previous week.
     */
    public void onPrevWeek() {
        weeklyViewModel.changeWeek(-1);
    }

    /**
     * Handles navigation to the next week.
     */
    public void onNextWeek() {
        weeklyViewModel.changeWeek(1);
    }

    /**
     * Handles the click event on a day header.
     * Delegates the creation of a new activity to the CreateActivityController.
     *
     * @param date the selected date
     */
    public void onDayClicked(LocalDate date) {
        CreateActivityController controller = new CreateActivityController(weeklyViewModel, date);
        controller.showActivityHandlerSignal();
    }

    /**
     * Handles the edit action for an existing activity.
     *
     * @param vm the view model of the selected activity
     */
    public void onEdit(ActivityViewModel vm) {
        EditActivityController controller = new EditActivityController(weeklyViewModel, vm);
        controller.showActivityHandlerSignal();
    }

    /**
     * Handles the remove action for an existing activity.
     *
     * @param vm the view model of the selected activity
     */
    public void onRemove(ActivityViewModel vm) {
        RemoveActivityController controller = new RemoveActivityController(weeklyViewModel, vm);
        controller.showActivityHandlerSignal();
    }

    /**
     * Manages selection of an activity view.
     * Ensures that only one activity at a time displays its completion button.
     *
     * @param view the selected activity view
     */
    public void onSelect(ActivityView view) {
        if (selectedActivityView != null && selectedActivityView != view)
            selectedActivityView.hideCheckButton();

        selectedActivityView = view;
        view.showCheckButton();
    }

    /**
     * Clears the current activity selection, if any.
     */
    public void clearSelection() {
        if (selectedActivityView != null) {
            selectedActivityView.hideCheckButton();
            selectedActivityView = null;
        }
    }

    /**
     * Handles the completion request for an activity.
     *
     * @param view the selected activity view
     */
    public void onComplete(ActivityView view) {
        CompleteActivityController controller = new CompleteActivityController(view, weeklyViewModel);
        controller.showCompleteActivity();
    }

    /**
     * Returns the list of projects available in the calendar.
     *
     * @return list of projects
     */
    public List<Project> getProjectList() {
        return weeklyViewModel.getCalendar().getProjects();
    }

    /**
     * Handles the visualization of a project report.
     *
     * @param project the selected project
     */
    public void projectReport(Project project) {
        ReportController reportController = new ReportController(weeklyViewModel, project);
        reportController.showReportSignal();
    }
}