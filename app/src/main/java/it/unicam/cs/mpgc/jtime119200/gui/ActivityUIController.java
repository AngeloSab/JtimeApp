package it.unicam.cs.mpgc.jtime119200.gui;

import it.unicam.cs.mpgc.jtime119200.*;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ActivityUIController {

    private final JtimeCalendar calendar;
    private Runnable onCalendarChanged;


    public ActivityUIController(JtimeCalendar calendar) {
        this.calendar = calendar;
    }

    public Day getDay(LocalDate date) {
        return calendar.getDay(date);
    }

    public JtimeCalendar getCalendar() {
        return calendar;
    }


    // ---------- GUI FORMS ----------

    public VBox newActivityBtnResponse(LocalDate date) {
        VBox form = new VBox(10);
        form.setAlignment(Pos.TOP_LEFT);

        Label titleLabel = new Label("New Activity");
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        // --- Nome attività ---
        TextField nameField = new TextField();
        nameField.setPromptText("Activity name");

        // --- Durata attività ---
        TextField durationField = new TextField();
        durationField.setPromptText("Duration in minutes");

        // --- ComboBox progetto (editable) ---
        ComboBox<Project> projectCombo = new ComboBox<>();
        projectCombo.getItems().addAll(calendar.getProjects());
        projectCombo.setPromptText("Select or type a project");
        projectCombo.setEditable(true);

        // --- Orario inizio ---
        TextField startTimeField = new TextField();
        startTimeField.setPromptText("Start time (HH:mm)");

        // --- Pulsante Save ---
        Button saveBtn = new Button("Save");
        saveBtn.setOnAction(e -> {
            String name = nameField.getText();
            if (name.isEmpty()) throw new IllegalArgumentException("Name cannot be empty");
            long minutes;
            try {
                minutes = Long.parseLong(durationField.getText());
            } catch (NumberFormatException ex) {
                throw new IllegalArgumentException("Duration must be a number");
            }
            Duration duration = Duration.ofMinutes(minutes);

            // --- Gestione progetto ---
            String projectText = projectCombo.getEditor().getText().trim();
            if (projectText.isEmpty()) {
                throw new IllegalArgumentException("Project cannot be empty");
            }

            Project selectedProject = null;
            for (Project p : calendar.getProjects()) {
                if (p.getName().equals(projectText)) {
                    selectedProject = p;
                    break;
                }
            }
            if (selectedProject == null) {
                selectedProject = new Project(projectText);
                calendar.addProject(selectedProject); // aggiungi nuovo progetto
            }

            // --- Gestione orario inizio ---
            Instant startTime;
                String[] parts = startTimeField.getText().split(":");
                int hour = Integer.parseInt(parts[0]);
                int minute = Integer.parseInt(parts[1]);
                startTime = date.atTime(hour, minute)
                        .atZone(java.time.ZoneId.systemDefault()).toInstant();


            // --- Creazione attività ---
            calendar.createActivity(selectedProject, name, duration, startTime, date);
            if (onCalendarChanged != null) {
                onCalendarChanged.run();
            }
        });

        form.getChildren().addAll(titleLabel, nameField,
                durationField, projectCombo, startTimeField, saveBtn);

        return form;
    }


    public VBox removeActivityBtnResponse(LocalDate date) {
        VBox form = new VBox(10);
        form.setAlignment(Pos.CENTER);
        form.setSpacing(10);

        Label title = new Label("Remove Activity");
        Day day = calendar.getDay(date);
        List<Activity> activities = day.getActivities();
        ComboBox<Activity> activityCombo = new ComboBox<>();
        activityCombo.getItems().addAll(activities);


        Button saveBtn = new Button("Save");
        saveBtn.setOnAction(e -> {
            Activity selectedActivity = activityCombo.getValue();
            calendar.removeActivity(selectedActivity.getProject(), selectedActivity);
            if (onCalendarChanged != null) {
                onCalendarChanged.run();
            }
        });
        form.getChildren().addAll(activityCombo, saveBtn);

        return form;
    }

    public VBox editActivityBtnResponse(LocalDate date) {
        VBox form = new VBox(10);
        form.setAlignment(Pos.CENTER);

        Label title = new Label("Edit Activity");

        Day day = calendar.getDay(date);
        if (day == null) return form;

        // -------- ACTIVITY SELECTION --------
        ComboBox<Activity> activityCombo = new ComboBox<>();
        activityCombo.getItems().addAll(day.getActivities());
        activityCombo.setPromptText("Select activity");

        // -------- PROJECT --------
        ComboBox<Project> projectCombo = new ComboBox<>();
        projectCombo.getItems().addAll(calendar.getProjects());
        projectCombo.setPromptText("Select project");
        projectCombo.setEditable(false); // 🔒 evita ClassCastException

        // -------- START TIME --------
        TextField timeField = new TextField();
        timeField.setPromptText("HH:mm");

        // -------- DURATION --------
        TextField durationField = new TextField();
        durationField.setPromptText("Minutes (0–900)");

        Button saveBtn = new Button("Save");

        saveBtn.setOnAction(e -> {
            Activity selectedActivity = activityCombo.getValue();
            if (selectedActivity == null) return;

            // ================= PROJECT =================
            Project selectedProject = projectCombo.getValue();
            if (selectedProject != null) {
                selectedActivity.setProject(selectedProject);
            }

            // ================= START TIME =================
            Instant startTime = selectedActivity.getStartTime();
            if (!timeField.getText().isBlank()) {
                    String[] parts = timeField.getText().split(":");
                    if (parts.length != 2) throw new IllegalArgumentException();

                    int hour = Integer.parseInt(parts[0]);
                    int minute = Integer.parseInt(parts[1]);

                    if (hour < 0 || hour > 23 || minute < 0 || minute > 59) {
                        throw new IllegalArgumentException("Time must be between 00:00 and 23:59");
                    }

                    startTime = date
                            .atTime(hour, minute)
                            .atZone(ZoneId.systemDefault())
                            .toInstant();

            }

            // ================= DURATION =================
            Duration duration = selectedActivity.getExpectedDuration();
            if (!durationField.getText().isBlank()) {
                    int minutes = Integer.parseInt(durationField.getText());
                    if (minutes < 0 || minutes > 900) throw new IllegalArgumentException();
                    duration = Duration.ofMinutes(minutes);
            }

            // ================= APPLY CHANGES =================
            selectedActivity.setStartTime(startTime);
            selectedActivity.setExpectedDuration(duration);

            if (onCalendarChanged != null) {
                onCalendarChanged.run();
            }
        });

        form.getChildren().addAll(
                title,
                activityCombo,
                projectCombo,
                timeField,
                durationField,
                saveBtn
        );

        return form;
    }





    public void setOnCalendarChanged(Runnable onCalendarChanged) {
        this.onCalendarChanged = onCalendarChanged;

    }
}
