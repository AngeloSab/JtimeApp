package it.unicam.cs.mpgc.jtime119200.gui.form;

import it.unicam.cs.mpgc.jtime119200.controllers.activityHandler.RemoveActivityController;
import it.unicam.cs.mpgc.jtime119200.model.ActivityViewModel;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Form used to confirm the removal of an activity.
 * Displays activity details and provides confirm and cancel options.
 */
public class RemoveActivityForm {
    private final ActivityViewModel activityViewModel;
    private final RemoveActivityController removeActivityController;
    Stage stage = new Stage();

    /**
     * Constructs the removal form for a specific activity.
     *
     * @param removeActivityController the controller managing the removal
     * @param activity the activity to be removed
     */
    public RemoveActivityForm(RemoveActivityController removeActivityController, ActivityViewModel activity) {
        this.activityViewModel = activity;
        this.removeActivityController = removeActivityController;

        stage.setTitle("Activity Remove");
        Scene scene = new Scene(createRoot());
        stage.setScene(scene);
        stage.setWidth(350);
        stage.setHeight(300);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        stage.show();
    }

    /**
     * Creates the main layout of the removal confirmation form,
     * including activity details and action buttons.
     *
     * @return the root node of the form
     */
    private Parent createRoot() {

        BorderPane root = new BorderPane();
        root.getStyleClass().add("removeActivityForm-root");

        VBox infoBox = new VBox(10);
        infoBox.getStyleClass().add("removeActivityForm-infoBox");

        Label confirmation = new Label("Are you sure you want to remove this activity?");
        confirmation.getStyleClass().add("removeActivityForm-dialog-confirmation");
        Label activityTitle = new Label("Title: " + activityViewModel.getTitle());
        activityTitle.getStyleClass().add("removeActivityForm-info");
        Label project = new Label("Project: " + activityViewModel.getProject());
        project.getStyleClass().add("removeActivityForm-info");
        Label date = new Label("Date: " + activityViewModel.getDate().toString());
        date.getStyleClass().add("removeActivityForm-info");

        infoBox.getChildren().addAll(confirmation, activityTitle, project, date);

        root.setCenter(infoBox);

        Button confirm = new Button("Confirm");
        confirm.setOnAction(e -> {
            removeActivityController.removeActivity(activityViewModel);
            stage.close();
        });
        confirm.getStyleClass().add("removeActivityForm-dialog-confirm");

        Button cancel = new Button("Cancel");
        cancel.getStyleClass().add("removeActivityForm-dialog-cancel");
        cancel.setOnAction(e -> stage.close());

        HBox buttonBox = new HBox(15, confirm, cancel);
        buttonBox.getStyleClass().add("removeActivityForm-buttons");

        root.setBottom(buttonBox);

        return root;
    }
}