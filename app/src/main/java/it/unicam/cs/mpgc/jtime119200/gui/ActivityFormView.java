package it.unicam.cs.mpgc.jtime119200.gui;

import it.unicam.cs.mpgc.jtime119200.domain.Activity;
import it.unicam.cs.mpgc.jtime119200.domain.Project;
import it.unicam.cs.mpgc.jtime119200.model.ActivityFormViewModel;
import it.unicam.cs.mpgc.jtime119200.gui.contract.ActivityHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;

public class ActivityFormView {

    private final ActivityFormViewModel viewModel;
    private final ActivityHandler handler;

    public ActivityFormView(ActivityFormViewModel viewModel, ActivityHandler handler) {
        this.viewModel = viewModel;
        this.handler = handler;
    }

    public void show(LocalDate date) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Add New Activity");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setVgap(5);
        grid.setHgap(5);

        // Title
        TextField titleField = new TextField();
        grid.add(new Label("Title:"), 0, 0);
        grid.add(titleField, 1, 0);

        // Project
        ComboBox<Project> projectCombo = new ComboBox<>();
        projectCombo.getItems().addAll(viewModel.getAllProjects());
        grid.add(new Label("Project:"), 0, 1);
        grid.add(projectCombo, 1, 1);

        // Expected Duration (in minutes)
        TextField durationField = new TextField();
        grid.add(new Label("Duration (min):"), 0, 2);
        grid.add(durationField, 1, 2);

        // Start Time (hour:minute)
        TextField startTimeField = new TextField();
        grid.add(new Label("Start Time (HH:mm):"), 0, 3);
        grid.add(startTimeField, 1, 3);

        Button submit = new Button("Create");
        grid.add(submit, 1, 4);

        submit.setOnAction(e -> {
            try {
                String title = titleField.getText();
                Project project = projectCombo.getValue();
                Duration duration = Duration.ofMinutes(Long.parseLong(durationField.getText()));
                String[] parts = startTimeField.getText().split(":");
                Instant startTime = date.atTime(Integer.parseInt(parts[0]), Integer.parseInt(parts[1])).atZone(java.time.ZoneId.systemDefault()).toInstant();

                Activity activity = viewModel.buildActivity(project, title, duration, startTime, date);
                handler.handle(activity);
                stage.close();
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid input: " + ex.getMessage());
                alert.showAndWait();
            }
        });

        stage.setScene(new Scene(grid));
        stage.showAndWait();
    }
}
