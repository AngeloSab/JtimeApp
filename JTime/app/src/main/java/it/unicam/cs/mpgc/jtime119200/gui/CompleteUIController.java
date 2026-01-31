package it.unicam.cs.mpgc.jtime119200.gui;

import it.unicam.cs.mpgc.jtime119200.Activity;
import it.unicam.cs.mpgc.jtime119200.Day;
import it.unicam.cs.mpgc.jtime119200.JtimeCalendar;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import javax.swing.*;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.function.Consumer;

public class CompleteUIController {

    private JtimeCalendar calendar;
    private Runnable onCalendarChanged;
    private Consumer<Activity> onActivityCompleted;



    public CompleteUIController(JtimeCalendar calendar) {
        this.calendar = calendar;
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


        completeBox.setOnAction(e -> {
            Spinner<Integer> durationSpinner = new Spinner<Integer>(0, 900, ((int) activity.getExpectedDuration().toMinutes()));
            durationSpinner.setPromptText("Insert Actual Duration in Minutes");
            durationSpinner.setMaxWidth(140);
            Button saveButton = new Button("Save");
            saveButton.setOnAction(f -> {
                int actualDuration = durationSpinner.getValue();
                activity.complete(Duration.ofMinutes(actualDuration));
                if (onCalendarChanged != null) onCalendarChanged.run();
                if (onActivityCompleted != null) onActivityCompleted.accept(activity);

            });
            checkStatusBox.getChildren().add(durationSpinner);
            checkStatusBox.getChildren().add(saveButton);
        });

        return checkStatusBox;
    }

    public Node printReport(Activity activity) {
        return null;
    }

    public void setOnCalendarChanged(Runnable e) {
        this.onCalendarChanged = e;
    }
    public void setOnActivityCompleted(Consumer<Activity> callback) {this.onActivityCompleted = callback;}

}

