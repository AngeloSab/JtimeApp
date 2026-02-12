package it.unicam.cs.mpgc.jtime119200.gui.form;

import it.unicam.cs.mpgc.jtime119200.application.RemoveActivityController;
import it.unicam.cs.mpgc.jtime119200.model.ActivityViewModel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RemoveActivityForm {
    private final ActivityViewModel activityViewModel;
    private final RemoveActivityController removeActivityController;
    Stage stage = new Stage();

    public RemoveActivityForm(RemoveActivityController removeActivityController, ActivityViewModel activity) {
        this.activityViewModel = activity;
        this.removeActivityController = removeActivityController;

        stage.setTitle(activityViewModel.getTitle());
        stage.setScene(new Scene(createRoot(), 400, 350));
        stage.show();
    }

    private Parent createRoot() {

        BorderPane root = new BorderPane();
        root.getStyleClass().add("root");

        Label title = new Label("Remove Activity");
        title.getStyleClass().add("dialog-title");
        BorderPane.setAlignment(title, Pos.CENTER);
        root.setTop(title);

        // Info box
        VBox infoBox = new VBox(10);

        Label confirmation = new Label("Are you sure you want to remove this activity?");
        confirmation.getStyleClass().add("dialog-confirmation");
        Label activityTitle = new Label("Title: " + activityViewModel.getTitle());
        activityTitle.getStyleClass().add("dialog-title");
        Label project = new Label("Project: " + activityViewModel.getProject());
        project.getStyleClass().add("dialog-project");
        Label date = new Label("Date: " + activityViewModel.getDate().toString());
        date.getStyleClass().add("dialog-date");

        infoBox.getChildren().addAll(confirmation, activityTitle, project, date);

        root.setCenter(infoBox);

        Button confirm = new Button("Confirm");
        confirm.setOnAction(e -> {
            removeActivityController.removeActivity(activityViewModel);
            stage.close();
        });
        confirm.getStyleClass().add("dialog-confirm");

        Button cancel = new Button("Cancel");
        cancel.getStyleClass().add("dialog-cancel");
        cancel.setOnAction(e -> stage.close());

        HBox buttonBox = new HBox(15, confirm, cancel);

        root.setBottom(buttonBox);

        return root;
    }

}
