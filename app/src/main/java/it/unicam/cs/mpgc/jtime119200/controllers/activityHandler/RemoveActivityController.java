package it.unicam.cs.mpgc.jtime119200.controllers.activityHandler;

import it.unicam.cs.mpgc.jtime119200.domain.JtimeCalendar;
import it.unicam.cs.mpgc.jtime119200.gui.form.RemoveActivityForm;
import it.unicam.cs.mpgc.jtime119200.model.ActivityViewModel;
import it.unicam.cs.mpgc.jtime119200.model.WeeklyViewModel;

/**
 * Controller responsible for removing an activity from the calendar.
 * This class acts as an ActivityHandler and is responsible for:
 *  - Managing user interactions related to removing an activity
 *  - Communicating with the {@link JtimeCalendar} domain object to delete the activity
 *  - Updating the {@link WeeklyViewModel} to refresh the UI after removal
 *  - Launching the removal confirmation form
 */
public class RemoveActivityController implements HandleActivityInterface {

    private final WeeklyViewModel weeklyViewModel;
    private final ActivityViewModel activityViewModel;
    private final JtimeCalendar calendar;

    /**
     * Constructs a new RemoveActivityController for a specific activity.
     *
     * @param weeklyViewModel the view model of the current week
     * @param activity the activity to remove
     */
    public RemoveActivityController(WeeklyViewModel weeklyViewModel, ActivityViewModel activity) {
        this.weeklyViewModel = weeklyViewModel;
        this.activityViewModel = activity;
        this.calendar = weeklyViewModel.getCalendar();
    }

    /**
     * Removes the specified activity from the calendar.
     *
     * @param activityViewModel the activity view model representing the activity to remove
     */
    public void removeActivity(ActivityViewModel activityViewModel) {
        calendar.removeActivity(activityViewModel.getActivity().getProject(), activityViewModel.getActivity());
        weeklyViewModel.notifyListeners();
    }

    /**
     * Opens the form for confirming and performing the removal of the selected activity.
     * This method launches a {@link RemoveActivityForm} to allow the user
     * to confirm the deletion of the activity.
     */
    @Override
    public void showActivityHandlerSignal() {
        new RemoveActivityForm(this, activityViewModel);
    }

}