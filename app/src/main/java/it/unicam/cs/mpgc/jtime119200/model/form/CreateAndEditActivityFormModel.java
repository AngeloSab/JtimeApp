package it.unicam.cs.mpgc.jtime119200.model.form;

import it.unicam.cs.mpgc.jtime119200.controllers.activityHandler.ActivityFormMode;
import it.unicam.cs.mpgc.jtime119200.controllers.activityHandler.CreateActivityController;
import it.unicam.cs.mpgc.jtime119200.controllers.activityHandler.EditActivityController;
import it.unicam.cs.mpgc.jtime119200.model.ActivityViewModel;
import it.unicam.cs.mpgc.jtime119200.model.WeeklyViewModel;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;

/**
 * Model used by the create and edit activity forms.
 * Handles the form mode and delegates creation or editing of activities
 * to the appropriate controller.
 */
public class CreateAndEditActivityFormModel {

    private final ActivityFormMode mode;
    private final ActivityViewModel activity;
    private final LocalDate day;
    private CreateActivityController createController;
    private EditActivityController editController;

    /**
     * Constructs a model in create mode for a new activity.
     *
     * @param weeklyViewModel the weekly view model to which the activity belongs
     * @param day the day of the new activity
     */
    public CreateAndEditActivityFormModel(WeeklyViewModel weeklyViewModel, LocalDate day) {
        this.day = day;
        this.activity = null;
        this.mode = ActivityFormMode.CREATE;
        this.createController = new CreateActivityController(weeklyViewModel, day);
    }

    /**
     * Constructs a model in edit mode for an existing activity.
     *
     * @param weeklyViewModel the weekly view model containing the activity
     * @param activity the activity to edit
     * @param day the day of the activity
     */
    public CreateAndEditActivityFormModel(WeeklyViewModel weeklyViewModel, ActivityViewModel activity, LocalDate day) {
        this.activity = activity;
        this.day = day;
        this.mode = ActivityFormMode.EDIT;
        this.editController = new EditActivityController(weeklyViewModel, activity);
    }

    /**
     * Returns the current form mode (CREATE or EDIT).
     *
     * @return the activity form mode
     */
    public ActivityFormMode getMode() {
        return mode;
    }

    /**
     * Submits the form data to either create a new activity or edit an existing one,
     * depending on the current form mode.
     *
     * @param projectName the name of the project
     * @param title the title of the activity
     * @param expectedDuration the expected duration of the activity
     * @param startTime the start time of the activity
     */
    public void submit(String projectName, String title, Duration expectedDuration, Instant startTime) {
        if (mode == ActivityFormMode.CREATE) {
            createController.createActivity(
                    projectName,
                    title,
                    expectedDuration,
                    startTime,
                    day
            );
            return;
        }

        if (mode == ActivityFormMode.EDIT) {
            editController.editActivity(projectName, expectedDuration, startTime);
            return;
        }

        throw new IllegalStateException("Unknown form mode");
    }

    /**
     * Returns the activity being edited, or null if the form is in create mode.
     *
     * @return the activity view model to edit
     */
    public ActivityViewModel getActivityToEdit() {
        return activity;
    }

}