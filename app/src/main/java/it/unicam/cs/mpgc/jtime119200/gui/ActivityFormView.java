package it.unicam.cs.mpgc.jtime119200.gui;

import it.unicam.cs.mpgc.jtime119200.application.ActivityFormMode;
import it.unicam.cs.mpgc.jtime119200.domain.Activity;
import it.unicam.cs.mpgc.jtime119200.model.ActivityFormModel;
import it.unicam.cs.mpgc.jtime119200.model.ActivityViewModel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.*;

public class ActivityFormView {

    private final Stage stage;
    private final ActivityFormModel controller;
    private final LocalDate day;

    private TextField projectField;
    private TextField titleField;
    private TextField startTimeField;
    private TextField durationField;

    public ActivityFormView(Stage stage,
                            ActivityFormModel controller,
                            LocalDate day) {
        this.stage = stage;
        this.controller = controller;
        this.day = day;

        stage.setTitle(getTitle());
        stage.setScene(new Scene(createRoot(), 400, 350));
        stage.show();
    }

    private String getTitle() {
        return controller.getMode() == ActivityFormMode.CREATE
                ? "Create new Activity"
                : "Edit Activity";
    }

    private Parent createRoot() {
        VBox root = new VBox(10);
        root.setPadding(new Insets(15));
        root.setAlignment(Pos.CENTER);

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

    private void preloadFields() {
        ActivityViewModel activityVM = controller.getActivityToEdit();

        projectField.setText(activityVM.getProjectName());
        titleField.setText(activityVM.getTitle());
        startTimeField.setText(activityVM.getStartTimeAsString());
        durationField.setText(activityVM.getExpectedDurationMinutes());
    }

    private HBox createButtons() {
        Button submit = new Button(
                controller.getMode() == ActivityFormMode.CREATE
                        ? "Create"
                        : "Save"
        );

        submit.setOnAction(e -> onSubmit());

        Button cancel = new Button("Cancel");
        cancel.setOnAction(e -> stage.close());

        HBox box = new HBox(10, submit, cancel);
        box.setAlignment(Pos.CENTER);
        return box;
    }

    private void onSubmit() {
        try {
            String project = projectField.getText();
            String title = titleField.getText();

            if (project.isBlank() || title.isBlank()) {
                throw new IllegalArgumentException("Project and title cannot be empty");
            }

            Duration duration = Duration.ofMinutes(
                    Long.parseLong(durationField.getText())
            );

            Instant startTime = parseStartTime();

            controller.submit(project, title, duration, startTime);
            stage.close();

        } catch (Exception ex) {
            showError(ex.getMessage());
        }
    }

    private Instant parseStartTime() {
        LocalTime time = LocalTime.parse(startTimeField.getText());

        ZonedDateTime zdt = ZonedDateTime.of(
                day,
                time,
                ZoneId.of("UTC+1")
        );

        return zdt.toInstant();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Invalid data");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
