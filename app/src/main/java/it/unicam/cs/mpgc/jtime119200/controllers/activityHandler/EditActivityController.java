package it.unicam.cs.mpgc.jtime119200.controllers.activityHandler;

import it.unicam.cs.mpgc.jtime119200.domain.JtimeCalendar;
import it.unicam.cs.mpgc.jtime119200.gui.form.CreateAndEditActivityForm;
import it.unicam.cs.mpgc.jtime119200.model.form.CreateAndEditActivityFormModel;
import it.unicam.cs.mpgc.jtime119200.model.ActivityViewModel;
import it.unicam.cs.mpgc.jtime119200.model.WeeklyViewModel;

import java.time.Duration;
import java.time.Instant;

/**
 * Controller responsible for editing an existing activity in the calendar.
 * This class acts as an ActivityHandler and is responsible for:
 *  - Managing user interactions related to editing an activity
 *  - Communicating with the {@link JtimeCalendar} domain object to update activity data
 *  - Updating the {@link WeeklyViewModel} to refresh the UI after modifications
 *  - Opening the activity editing form pre-filled with current activity data
 */
public class EditActivityController implements HandleActivityInterface {

    private final WeeklyViewModel weeklyViewModel;
    private final ActivityViewModel activityViewModel;
    private final JtimeCalendar calendar;

    /**
     * Constructs a new EditActivityController for a specific activity.
     *
     * @param weeklyViewModel the view model of the current week
     * @param activity the activity to edit
     */
    public EditActivityController(WeeklyViewModel weeklyViewModel, ActivityViewModel activity) {
        this.weeklyViewModel = weeklyViewModel;
        this.activityViewModel = activity;
        this.calendar = weeklyViewModel.getCalendar();
    }

    /**
     * Edits the specified activity with new project, expected duration and start time.
     *
     * @param project the name of the project this activity belongs to
     * @param expectedDuration the updated expected duration of the activity
     * @param startTime the updated start time of the activity
     */
    public void editActivity(String project, Duration expectedDuration, Instant startTime) {
        calendar.editActivity(activityViewModel.getActivity(), calendar.createProject(project), expectedDuration, startTime);
        weeklyViewModel.notifyListeners();
    }

    /**
     * Opens the form for editing the selected activity.
     * This method constructs a {@link CreateAndEditActivityFormModel}
     * pre-filled with the current activity data and launches a
     * {@link CreateAndEditActivityForm} for editing.
     */
    @Override
    public void showActivityHandlerSignal() {
        CreateAndEditActivityFormModel afm = new CreateAndEditActivityFormModel(weeklyViewModel, activityViewModel, activityViewModel.getDate());
        new CreateAndEditActivityForm(afm, activityViewModel.getDate());
    }
}