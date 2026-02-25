package it.unicam.cs.mpgc.jtime119200.gui.form;

import it.unicam.cs.mpgc.jtime119200.controllers.activityHandler.ActivityFormMode;
import it.unicam.cs.mpgc.jtime119200.domain.service.TimeServiceProvider;
import it.unicam.cs.mpgc.jtime119200.model.form.CreateAndEditActivityFormModel;
import it.unicam.cs.mpgc.jtime119200.model.ActivityViewModel;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.*;

/**
 * Form used to create a new activity or edit an existing one.
 * Provides fields for project, title, start time, and expected duration.
 * Handles validation and submission through the associated controller.
 */
public class CreateAndEditActivityForm extends TimeServiceProvider {

    private final Stage stage = new Stage();
    private final CreateAndEditActivityFormModel controller;
    private final LocalDate day;

    private TextField projectField;
    private TextField titleField;
    private TextField startTimeField;
    private TextField durationField;

    /**
     * Constructs the form for creating or editing an activity.
     *
     * @param controller the controller managing the form actions
     * @param day the day for which the activity is being created or edited
     */
    public CreateAndEditActivityForm(CreateAndEditActivityFormModel controller, LocalDate day) {
        this.controller = controller;
        this.day = day;

        stage.setTitle(getTitle());
        stage.setScene(new Scene(createRoot(), 400, 350));
        stage.show();
    }

    /**
     * Returns the window title based on the mode (create or edit).
     *
     * @return the title string
     */
    private String getTitle() {
        return controller.getMode() == ActivityFormMode.CREATE
                ? "Create new Activity"
                : "Edit Activity";
    }

    /**
     * Creates the main layout of the form including input fields and buttons.
     *
     * @return the root parent node
     */
    private Parent createRoot() {
        VBox root = new VBox(10);
        root.getStyleClass().add("dialog-root");

        projectField = new TextField();
        projectField.setPromptText("Project");

        titleField = new TextField();
        titleField.setPromptText("Title");

        startTimeField = new TextField();
        startTimeField.setPromptText("Start time (HH:mm)");

        durationField = new TextField();
        durationField.setPromptText("Expected duration (minutes)");

        if (controller.getMode() == ActivityFormMode.EDIT) {
            preloadFields();
        }

        root.getChildren().addAll(
                new Label("Project"),
                projectField,
                new Label("Title"),
                titleField,
                new Label("Start time"),
                startTimeField,
                new Label("Duration"),
                durationField,
                createButtons()
        );

        return root;
    }

    /**
     * Preloads the input fields with values from the activity being edited.
     */
    private void preloadFields() {
        ActivityViewModel activityVM = controller.getActivityToEdit();

        projectField.setText(activityVM.getProjectName());
        titleField.setText(activityVM.getTitle());
        startTimeField.setText(activityVM.getStartTime());
        durationField.setText(activityVM.getExpectedDurationMinutes());
    }

    /**
     * Creates the submit and cancel buttons for the form.
     *
     * @return a horizontal box containing the buttons
     */
    private HBox createButtons() {
        Button submit = new Button(controller.getMode() == ActivityFormMode.CREATE ? "Create" : "Save");
        submit.getStyleClass().add("dialog-submit");
        submit.setOnAction(e -> onSubmit());

        Button cancel = new Button("Cancel");
        cancel.getStyleClass().add("dialog-cancel");
        cancel.setOnAction(e -> stage.close());

        return new HBox(10, submit, cancel);
    }

    /**
     * Handles the submission of the form.
     * Validates input, converts data types, and delegates to the controller.
     */
    private void onSubmit() {
        try {
            String project = projectField.getText();
            if (project.isEmpty()) throw new Exception("Project name cannot be empty");
            String title = titleField.getText();
            if (title.isEmpty()) throw new Exception("Activity name cannot be empty");

            Duration duration = Duration.ofMinutes(Long.parseLong(durationField.getText()));
            if (duration.isNegative() || duration.toMinutes() > 900) throw new Exception("Invalid duration");

            Instant startTime = parseStartTime();

            controller.submit(project, title, duration, startTime);
            stage.close();

        } catch (Exception ex) {
            showError(ex.getMessage());
        }
    }

    /**
     * Parses the start time from the text field and converts it to an Instant.
     *
     * @return the parsed start time as Instant
     */
    private Instant parseStartTime() {
        LocalTime time = LocalTime.parse(startTimeField.getText());
        ZonedDateTime zdt = ZonedDateTime.of(day, time, getTimeZone());
        return zdt.toInstant();
    }

    /**
     * Shows an error alert with the given message.
     *
     * @param message the message to display in the alert
     */
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Invalid data");
        alert.setContentText(message);
        alert.showAndWait();
    }
}