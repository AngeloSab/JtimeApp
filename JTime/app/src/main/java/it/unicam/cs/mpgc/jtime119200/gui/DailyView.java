package it.unicam.cs.mpgc.jtime119200.gui;

import it.unicam.cs.mpgc.jtime119200.Activity;
import it.unicam.cs.mpgc.jtime119200.Day;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;


public class DailyView extends VBox {

    private final ActivityUIController controller;
    private final CompleteUIController completeController;
    private final LocalDate date;
    private final Runnable onBack;
    private final VBox right = new VBox();

    public DailyView(ActivityUIController controller, CompleteUIController completeController, LocalDate date, Runnable onBack) {
        this.controller = controller;
        this.date = date;
        this.onBack = onBack;
        this.completeController = completeController;
        refresh();
    }

    private void refresh() {
        this.getChildren().clear();

        Label dateLabel = new Label("Day: " + date);
        dateLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        this.getChildren().add(dateLabel);

        buildDay();
    }

    private void buildDay() {
        BorderPane root = new BorderPane();
        root.setTop(createTopButtons());
        root.setCenter(createCenter());
        root.setRight(createRight());

        this.getChildren().add(root);
    }

    private VBox createRight() {
        right.setAlignment(Pos.CENTER_RIGHT);
        right.setSpacing(5);
        right.setStyle("-fx-border-color: black; -fx-border-width: 1;");
        right.setMinHeight(550);
        right.setMinWidth(400);
        VBox.setVgrow(right, Priority.ALWAYS);
        return right;
    }

    private VBox createCenter() {
        VBox center = new VBox(5);
        center.setAlignment(Pos.TOP_LEFT);
        center.setStyle("-fx-border-color: black; -fx-border-width: 1;");
        center.setMinHeight(550);
        center.setMinWidth(450);
        VBox.setVgrow(center, Priority.ALWAYS);

        Day day = controller.getDay(date);

        for (Activity activity : day.getActivities().stream().sorted().toList()) {
            VBox daysActivities = new VBox();
            daysActivities.setAlignment(Pos.CENTER);
            daysActivities.setSpacing(0);
            daysActivities.setMinHeight(75);
            daysActivities.setStyle("-fx-border-color: black; -fx-border-width: 1;");
            if (activity.isCompleted()) daysActivities.setStyle("-fx-border-color: red; -fx-border-width: 1;");
            daysActivities.setOnMouseClicked(e -> handleCompleteActivityCheck(activity));
            Label description = new Label(
                     activity.getTitle() + " (Project: " + activity.getProject().getName() + ")" + System.lineSeparator() +
                            "Start Time: " +
                    activity.getStartTime().atZone(ZoneId.of("UTC+1")).format(DateTimeFormatter.ofPattern("HH:mm")) +
                    ", Expected Duration: " + activity.getExpectedDuration().toMinutes() + " min " +
                    ", Expected End Time : " +
                    activity.expectedEndTime().atZone(ZoneId.of("UTC+1")).format(DateTimeFormatter.ofPattern("HH:mm")) + " )"
                    + System.lineSeparator() + "Completed:  " + activity.isCompleted());
            daysActivities.getChildren().add(description);
            center.getChildren().add(daysActivities);
        }

        return center;
    }

    public HBox createTopButtons() {
        HBox buttons = new HBox(10);

        Button backView = new Button("Return in Weekly View");
        backView.setOnAction(e -> onBack.run());

        Button newActivityBtn = new Button("Create New Activity");
        newActivityBtn.setOnAction(this::handleNewActivity);

        Button removeActivityBtn = new Button("Remove an Activity");
        removeActivityBtn.setOnAction(this::handleRemoveActivity);

        Button editActivityBtn = new Button("Edit an Activity");
        editActivityBtn.setOnAction(this::handleEditActivity);

        buttons.getChildren().addAll(backView, newActivityBtn, removeActivityBtn, editActivityBtn);
        return buttons;
    }

    public void showInRight(Node content) {
        right.getChildren().clear();
        right.getChildren().add(content);
    }


    public void handleCompleteActivityCheck(Activity a){
        Node form = completeController.checkStatus(a);
        showInRight(form);

    }

    private void handleNewActivity(ActionEvent e) {
        Node form = controller.newActivityBtnResponse(this.date);
        showInRight(form);
    }

    private void handleRemoveActivity(ActionEvent e) {
        Node form = controller.removeActivityBtnResponse(this.date);
        showInRight(form);
    }

    private void handleEditActivity(ActionEvent e) {
        Node form = controller.editActivityBtnResponse(this.date);
        showInRight(form);
    }
}
