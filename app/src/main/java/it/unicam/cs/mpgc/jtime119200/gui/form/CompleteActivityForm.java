package it.unicam.cs.mpgc.jtime119200.gui.form;

import it.unicam.cs.mpgc.jtime119200.controllers.completeHandler.CompleteActivityController;
import it.unicam.cs.mpgc.jtime119200.model.ActivityViewModel;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.Duration;

/**
 * Form used to complete an activity by entering its actual duration.
 * Displays a text field for the duration and provides confirm and cancel buttons.
 */
public class CompleteActivityForm extends VBox {

    private final CompleteActivityController controller;

    /**
     * Constructs the form for completing an activity.
     *
     * @param viewModel the view model representing the activity
     * @param completeActivityController the controller handling form actions
     */
    public CompleteActivityForm(ActivityViewModel viewModel, CompleteActivityController completeActivityController) {
        this.controller = completeActivityController;
        this.getStyleClass().add("completeActivityForm");

        Label titleLabel = new Label("Complete activity: " + viewModel.getTitle());
        titleLabel.getStyleClass().add("CompleteActivityForm-title");

        TextField durationField = new TextField();
        durationField.requestFocus();
        durationField.setPromptText("Insert actual Duration");
        durationField.getStyleClass().add("CompleteActivityForm-title");

        HBox buttons = new HBox(2);
        Button confirmButton = new Button("Complete");
        confirmButton.setOnAction(event -> {
            try {
                Duration duration = Duration.ofMinutes(Long.parseLong(durationField.getText()));
                if (duration.toMinutes() <= 0 || duration.toMinutes() > 900) throw new Exception("Invalid duration");
                controller.confirmComplete(duration);
            } catch (Exception e) {
                showError(e.getMessage());
            }
        });
        confirmButton.getStyleClass().add("CompleteActivityForm-confirmButton");

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(event -> controller.cancelComplete());
        cancelButton.getStyleClass().add("CompleteActivityForm-cancelButton");

        buttons.getChildren().addAll(confirmButton, cancelButton);

        this.getChildren().addAll(titleLabel, durationField, buttons);
    }

    /**
     * Displays an error alert with the given message.
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