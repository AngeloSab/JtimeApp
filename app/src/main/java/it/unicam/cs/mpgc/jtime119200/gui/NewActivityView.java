package it.unicam.cs.mpgc.jtime119200.gui;

import it.unicam.cs.mpgc.jtime119200.domain.Project;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class NewActivityView extends VBox {

    private boolean confirmed = false;

    private final LocalDate date;

    private final TextField titleField = new TextField();
    private final TextField durationField = new TextField();
    private final TextField startTimeField = new TextField();
    private final ComboBox<Project> projectBox = new ComboBox<>();

    private final Label errorLabel = new Label();

    public NewActivityView(LocalDate date) {
        this.date = date;
        this.projectBox.getItems().addAll();
        buildLayout();
    }

    // ---------------- UI ----------------

    private void buildLayout() {
        setSpacing(10);
        setPadding(new Insets(15));
        setAlignment(Pos.TOP_LEFT);

        Label title = new Label("New Activity");
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        errorLabel.setStyle("-fx-text-fill: red;");

        Button createBtn = new Button("Create");
        Button cancelBtn = new Button("Cancel");

        createBtn.setOnAction(e -> onCreate());
        cancelBtn.setOnAction(e -> close());

        HBox buttons = new HBox(10, createBtn, cancelBtn);

        getChildren().addAll(
                title,
                new Label("Date"), new Label(date.toString()),
                new Label("Title"), titleField,
                new Label("Project"), projectBox,
                new Label("Expected duration (minutes)"), durationField,
                new Label("Start time (HH:mm)"), startTimeField,
                errorLabel,
                buttons
        );
    }

    // ---------------- Actions ----------------

    private void onCreate() {
        if (!isValid()) return;
        confirmed = true;
        close();
    }

    private void close() {
        getScene().getWindow().hide();
    }

    // ---------------- Validation ----------------

    private boolean isValid() {
        errorLabel.setText("");

        if (titleField.getText().isBlank()) {
            error("Title is required");
            return false;
        }

        if (projectBox.getValue() == null) {
            error("Project is required");
            return false;
        }

        try {
            long minutes = Long.parseLong(durationField.getText());
            if (minutes <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            error("Duration must be a positive number");
            return false;
        }

        try {
            LocalTime.parse(startTimeField.getText(),
                    DateTimeFormatter.ofPattern("HH:mm"));
        } catch (DateTimeParseException e) {
            error("Start time must be HH:mm");
            return false;
        }

        return true;
    }

    private void error(String msg) {
        errorLabel.setText(msg);
    }

    // ---------------- Getters ----------------

    public boolean isConfirmed() {
        return confirmed;
    }

    public String getTitle() {
        return titleField.getText();
    }

    public Project getProject() {
        return projectBox.getValue();
    }

    public Duration getExpectedDuration() {
        return Duration.ofMinutes(Long.parseLong(durationField.getText()));
    }

    public Instant getStartTime() {
        LocalTime time = LocalTime.parse(
                startTimeField.getText(),
                DateTimeFormatter.ofPattern("HH:mm")
        );
        return date.atTime(time)
                .atZone(ZoneId.of("UTC+1"))
                .toInstant();
    }
}
