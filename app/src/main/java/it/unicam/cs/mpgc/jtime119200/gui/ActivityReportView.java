package it.unicam.cs.mpgc.jtime119200.gui;

import it.unicam.cs.mpgc.jtime119200.model.ActivityReportViewModel;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ActivityReportView extends VBox {

    public ActivityReportView(ActivityReportViewModel reportVM, Runnable onClose) {
        setSpacing(10);
        setAlignment(Pos.TOP_LEFT);
        setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-padding: 10;");

        // -------- Info attività --------
        Label activityLabel = new Label("Activity: " + reportVM.getActivityTitle());
        Label projectLabel = new Label("Project: " + reportVM.getProjectName());
        Label durationsLabel = new Label(
                "Expected: " + reportVM.getExpectedDurationMinutes() + " min\n" +
                        "Actual: " + reportVM.getActualDurationMinutes() + " min\n" +
                        "Difference: " + reportVM.getEstimationDifferenceMinutes() + " min"
        );

        // -------- Progress bar progetto --------
        ProgressBar projectProgress = new ProgressBar(reportVM.getProjectProgress());
        Label progressLabel = new Label("Project Progress: " + Math.round(reportVM.getProjectProgress() * 100) + "%");

        VBox progressBox = new VBox(projectProgress, progressLabel);
        progressBox.setSpacing(5);
        progressBox.setAlignment(Pos.CENTER_LEFT);

        // -------- Project completato --------
        Label projectCompletedLabel = new Label();
        if (reportVM.isProjectCompleted()) {
            projectCompletedLabel.setText("Congratulations! Project completed successfully!");
        }

        // -------- Bottone chiusura --------
        Button closeBtn = new Button("Close");
        closeBtn.setOnAction(e -> onClose.run());

        // -------- Aggiungi tutto al layout --------
        getChildren().addAll(
                activityLabel,
                projectLabel,
                durationsLabel,
                progressBox,
                projectCompletedLabel,
                closeBtn
        );
    }
}

