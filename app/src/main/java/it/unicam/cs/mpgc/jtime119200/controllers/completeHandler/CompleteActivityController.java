package it.unicam.cs.mpgc.jtime119200.controllers.completeHandler;

import it.unicam.cs.mpgc.jtime119200.gui.ActivityView;
import it.unicam.cs.mpgc.jtime119200.gui.form.CompleteActivityForm;
import it.unicam.cs.mpgc.jtime119200.model.WeeklyViewModel;

import java.time.Duration;

/**
 * Controller responsible for handling the completion of an activity.
 * This class acts as a CompleteActivity handler and is responsible for:
 *  - Managing user interactions related to completing an activity
 *  - Updating the status and actual duration of the activity in the domain model
 *  - Refreshing the UI through the {@link WeeklyViewModel} after completion
 *  - Showing and hiding the completion confirmation form
 */
public class CompleteActivityController implements CompleteActivityInterface {

    private final ActivityView view;
    private final WeeklyViewModel viewModel;

    /**
     * Constructs a new CompleteActivityController for a specific activity.
     *
     * @param view the {@link ActivityView} representing the activity to complete
     * @param weeklyView the {@link WeeklyViewModel} of the current week
     */
    public CompleteActivityController(ActivityView view, WeeklyViewModel weeklyView) {
        this.view = view;
        this.viewModel = weeklyView;
    }

    /**
     * Confirms the completion of the activity and sets its actual duration.
     *
     * @param duration the actual duration spent on the activity
     */
    public void confirmComplete(Duration duration) {
        view.getViewModel().getActivity().complete(duration);
        view.hideCompleteForm();
        viewModel.notifyListeners();
    }

    /**
     * Cancels the completion operation and hides the completion form.
     */
    public void cancelComplete() {
        view.hideCompleteForm();
    }

    /**
     * Shows the form for completing the activity.
     * This method launches a {@link CompleteActivityForm} for the user
     * to confirm and submit the actual duration of the activity.
     */
    @Override
    public void showCompleteActivity() {
        CompleteActivityForm caf = new CompleteActivityForm(view.getViewModel(), this);
        view.showCompleteForm(caf);
    }
}