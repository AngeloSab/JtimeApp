package it.unicam.cs.mpgc.jtime119200.gui;

import it.unicam.cs.mpgc.jtime119200.Activity;
import it.unicam.cs.mpgc.jtime119200.JtimeCalendar;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.VBox;
import java.time.Duration;
import java.util.function.Consumer;

public class CompleteUIController {

    private Runnable onCalendarChanged;
    private Consumer<Activity> onActivityCompleted;



    public CompleteUIController(JtimeCalendar calendar) {
    }

    public Node checkStatus(Activity activity) {
        VBox checkStatusBox = new VBox();
        checkStatusBox.setSpacing(10);
        checkStatusBox.setAlignment(Pos.CENTER);
        checkStatusBox.setStyle("-fx-border-color: black;");
        checkStatusBox.setMinHeight(240);
        checkStatusBox.setMinWidth(100);
        CheckBox completeBox = new CheckBox("Completed Activity");
        completeBox.setAlignment(Pos.CENTER);
        checkStatusBox.getChildren().add(completeBox);

        VBox completePrompt = new VBox(5);
        checkStatusBox.getChildren().add(completePrompt);
        completePrompt.setAlignment(Pos.CENTER);
        Label actualDurationLabel = new Label("Insert Actual Duration");
        Spinner<Integer> durationSpinner = new Spinner<>(0, 900, ((int) activity.getExpectedDuration().toMinutes()));
        Button saveButton = new Button("Save");
        int actualDuration = durationSpinner.getValue();
        completeBox.setOnAction( e -> {
            completePrompt.setVisible(completeBox.isSelected());
            durationSpinner.setMaxWidth(140);
            saveButton.setOnAction(f -> {
                activity.complete(Duration.ofMinutes(actualDuration));
                if (onCalendarChanged != null) onCalendarChanged.run();
                if (onActivityCompleted != null) onActivityCompleted.accept(activity);
            });
            completePrompt.getChildren().addAll(actualDurationLabel, durationSpinner, saveButton);

        });
        return checkStatusBox;
    }

    public void setOnCalendarChanged(Runnable e) {
        this.onCalendarChanged = e;
    }
    public void setOnActivityCompleted(Consumer<Activity> callback) {this.onActivityCompleted = callback;}

}

