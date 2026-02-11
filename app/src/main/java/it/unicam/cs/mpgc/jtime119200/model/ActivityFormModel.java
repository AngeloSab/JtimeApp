package it.unicam.cs.mpgc.jtime119200.model;

import it.unicam.cs.mpgc.jtime119200.application.ActivityFormMode;
import it.unicam.cs.mpgc.jtime119200.application.CreateActivityController;
import it.unicam.cs.mpgc.jtime119200.application.EditActivityController;
import it.unicam.cs.mpgc.jtime119200.domain.Activity;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;

public class ActivityFormModel {

    private final WeeklyViewModel weeklyViewModel;
    private final ActivityFormMode mode;

    private final ActivityViewModel activity;
    private final LocalDate day;

    private CreateActivityController createController;
    private EditActivityController editController;

    /**
     * Costruttore per CREATE
     */
    public ActivityFormModel(WeeklyViewModel weeklyViewModel,
                             LocalDate day) {
        this.weeklyViewModel = weeklyViewModel;
        this.day = day;
        this.activity = null;
        this.mode = ActivityFormMode.CREATE;
        this.createController = new CreateActivityController(weeklyViewModel, day);
    }

    /**
     * Costruttore per EDIT
     */
    public ActivityFormModel(WeeklyViewModel weeklyViewModel,
                             ActivityViewModel activity
                             , LocalDate day) {
        this.weeklyViewModel = weeklyViewModel;
        this.activity = activity;
        this.day = day;
        this.mode = ActivityFormMode.EDIT;
        this.editController = new EditActivityController(weeklyViewModel, activity, day);
    }

    public ActivityFormMode getMode() {
        return mode;
    }

    /* =========================
       Metodi invocati dal FORM
       ========================= */

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

    public void cancel() {
        // TODO
    }

    /* =========================
       Supporto al FORM
       ========================= */

    public ActivityViewModel getActivityToEdit() {
        return activity;
    }

    public LocalDate getDayForCreation() {
        // TODO
        return null;
    }
}

