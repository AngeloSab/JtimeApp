package it.unicam.cs.mpgc.jtime119200.model.form;

import it.unicam.cs.mpgc.jtime119200.controllers.ActivityFormMode;
import it.unicam.cs.mpgc.jtime119200.controllers.CreateActivityController;
import it.unicam.cs.mpgc.jtime119200.controllers.EditActivityController;
import it.unicam.cs.mpgc.jtime119200.model.ActivityViewModel;
import it.unicam.cs.mpgc.jtime119200.model.WeeklyViewModel;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;

public class CreateAndEditActivityFormModel {

    private final WeeklyViewModel weeklyViewModel;
    private final ActivityFormMode mode;

    private final ActivityViewModel activity;
    private final LocalDate day;

    private CreateActivityController createController;
    private EditActivityController editController;

    public CreateAndEditActivityFormModel(WeeklyViewModel weeklyViewModel,
                                          LocalDate day) {
        this.weeklyViewModel = weeklyViewModel;
        this.day = day;
        this.activity = null;
        this.mode = ActivityFormMode.CREATE;
        this.createController = new CreateActivityController(weeklyViewModel, day);
    }

    public CreateAndEditActivityFormModel(WeeklyViewModel weeklyViewModel,
                                          ActivityViewModel activity
                             , LocalDate day) {
        this.weeklyViewModel = weeklyViewModel;
        this.activity = activity;
        this.day = day;
        this.mode = ActivityFormMode.EDIT;
        this.editController = new EditActivityController(weeklyViewModel, activity);
    }

    public ActivityFormMode getMode() {
        return mode;
    }


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

    public ActivityViewModel getActivityToEdit() {
        return activity;
    }

}

