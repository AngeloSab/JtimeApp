package it.unicam.cs.mpgc.jtime119200.gui;
import it.unicam.cs.mpgc.jtime119200.Activity;
import it.unicam.cs.mpgc.jtime119200.ActivityTimeCalculator;
import it.unicam.cs.mpgc.jtime119200.Day;
import it.unicam.cs.mpgc.jtime119200.ProjectProgressionCalculator;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

public class WeeklyView extends BorderPane {

    private final ActivityUIController controller;
    private final Consumer<LocalDate> onDaySelected;

    private final GridPane weekGrid = new GridPane();
    private final HBox reportBox = new HBox(10);

    private ActivityTimeCalculator activityCalculator;
    private ProjectProgressionCalculator progressionCalculator;


    public WeeklyView(ActivityUIController controller, Consumer<LocalDate> onDaySelected) {
        this.controller = controller;
        this.onDaySelected = onDaySelected;

        buildWeek();
    }

    private void buildWeek() {
        this.getChildren().clear();
        setCenter(weekGrid);
        weekGrid.setHgap(5);
        weekGrid.setVgap(5);

        //Row Constraints
        weekGrid.getRowConstraints().add(createHeaderRowConstraints());
        //Columns Constraints
        for (int i = 0; i < 7; i++) {
            weekGrid.getColumnConstraints().add(createHeaderColumnConstraints());
        }
        //Add labels
        createHeaderLabels();
        setBottom(reportBox);
    }

    private StackPane createDayHeader(LocalDate date) {
        StackPane header = new StackPane();
        header.setAlignment(Pos.CENTER);
        header.setMinHeight(75); // altezza fissa header
        header.setStyle("""
            -fx-border-color: black;
            -fx-border-width: 1;
            -fx-font-weight: bold;
            """);
        Label dateLabel = new Label(date.format(DateTimeFormatter.ofPattern("EEE dd MM")));
        header.getChildren().add(dateLabel);

        // click sul giorno → DailyView
        header.setOnMouseClicked(e -> onDaySelected.accept(date));

        return header;
    }

    public RowConstraints createHeaderRowConstraints(){
        RowConstraints headerRow = new RowConstraints();
        headerRow.setMinHeight(75);
        headerRow.setVgrow(Priority.NEVER);
        return headerRow;
    }

    public ColumnConstraints createHeaderColumnConstraints(){
        ColumnConstraints column = new ColumnConstraints();
        column.setPercentWidth(100.0 / 7);
        column.setHgrow(Priority.ALWAYS);
        return column;
    }

    public void createHeaderLabels(){
        Map<LocalDate, Day> days = weekPeriod();
        int col = 0;
        for (LocalDate date : days.keySet()) {
            // HEADER GIORNO
            StackPane header = createDayHeader(date);
            weekGrid.add(header, col, 0);

            // AREA ATTIVITÀ
            VBox activityArea = createActivityArea(days.get(date));
            weekGrid.add(activityArea, col, 1);

            col++;
        }
    }


    private VBox createActivityArea(Day day) {

        VBox box = new VBox();
        box.setAlignment(Pos.TOP_CENTER);
        box.setSpacing(5);
        box.setStyle("-fx-border-color: black; -fx-border-width: 1;");
        box.setMinHeight(400);
        VBox.setVgrow(box, Priority.ALWAYS);


        RowConstraints activitiesRow = new RowConstraints();
        activitiesRow.setVgrow(Priority.ALWAYS);
        weekGrid.getRowConstraints().add(activitiesRow);

        for (Activity activity : day.getActivities().stream().sorted().toList()) {
            VBox row = new VBox(3);
            row.setAlignment(Pos.TOP_CENTER);
            row.setStyle("-fx-border-color: black; -fx-border-width: 1;");
            if (activity.isCompleted()) row.setStyle("-fx-border-color: red; -fx-border-width: 1;");
            row.setMinHeight(75);
            box.getChildren().add(row);


            Label activityLabel =
                    new Label(activity.getStartTime().atZone(ZoneId.of("UTC+1")).format(DateTimeFormatter.ofPattern("HH:mm")) + " - " + activity.getTitle());
            row.getChildren().add(activityLabel);

            if (activity.isCompleted()) {
                Label completedActivityLabel = new Label(System.lineSeparator() + "Completed");
                row.getChildren().add(completedActivityLabel);
            }
            activityCalculator = new ActivityTimeCalculator(activity);
            Tooltip description = new Tooltip(activityDescription(activity));
            Tooltip.install(row, description);
            description.setShowDelay(new Duration(100));
            description.setShowDuration(new Duration(10000));

            activityLabel.setAlignment(Pos.CENTER);
            activityLabel.setStyle("-fx-font-size: 12px;");

        }
        return box;
    }

    public Map<LocalDate, Day> weekPeriod() {
        Map<LocalDate, Day> week = new LinkedHashMap<>();
        LocalDate today = LocalDate.now();

        for (int i = 0; i < 7; i++) {
            LocalDate date = today.plusDays(i);
            Day day = controller.getCalendar().getDay(date);
            week.put(date, day);
        }
        return week;
    }

    public void showReport(Activity activity) {
        progressionCalculator = new ProjectProgressionCalculator(activity.getProject());
        activityCalculator = new ActivityTimeCalculator(activity);
        reportBox.getChildren().clear();
        reportBox.setSpacing(20);
        reportBox.setAlignment(Pos.CENTER_LEFT);
        reportBox.setStyle("-fx-border-color: black; -fx-border-width: 1;");
        reportBox.setMinHeight(80);


        Label activityInfo1 = new Label("Activity " + activity.getTitle() + " Completed Successfully" + System.lineSeparator() +
                "Project: " + activity.getProject() + ";" +  System.lineSeparator() +
                "Start Time: " + activity.getStartTime().atZone(ZoneId.of("UTC+1")).format(DateTimeFormatter.ofPattern("HH:mm")) + ";");
        Label activityInfo2 = new Label("Expected Duration: " + activity.getExpectedDuration().toMinutes() + " (in minutes);" +  System.lineSeparator() +
                "Actual Duration: " + activity.getActualDuration().toMinutes() + " (in minutes);" +  System.lineSeparator() +
                "Estimation Difference: " + activityCalculator.estimationDifference().toMinutes() + " (in minutes);");

        reportBox.getChildren().addAll(activityInfo1, activityInfo2);
        VBox progressBar = getProgressBar(activity);

        VBox reportProject = reportProjectProgression(activity);
        reportProject.setAlignment(Pos.CENTER_LEFT);
        reportProject.setVisible(progressionCalculator.checkCompleted());


        Button exitButton = new Button("Exit");
        exitButton.setStyle("""
            -fx-border-color: red;
        """);
        exitButton.setAlignment(Pos.TOP_RIGHT);
        exitButton.setOnAction(e -> reportBox.setVisible(false));

        reportBox.getChildren().addAll(progressBar, reportProject, exitButton);
    }

    private VBox reportProjectProgression(Activity activity) {
        progressionCalculator = new ProjectProgressionCalculator(activity.getProject());
        VBox reportProjectProgression = new VBox();
        Label projectProgressionLabel = new Label("Congratulation! Project "+activity.getProject()+" Completed Successfully!" + System.lineSeparator()+
                "Activities Completed: "+progressionCalculator.getNumActivitiesCompleted()+", Activities Expired: "+progressionCalculator.getNumActivitiesExpired()+".");
        reportProjectProgression.getChildren().add(projectProgressionLabel);
        return reportProjectProgression;
    }

    private VBox getProgressBar(Activity a){
        ProgressBar progressBar = new ProgressBar(0.0);

        progressionCalculator = new ProjectProgressionCalculator(a.getProject());

        double progression = progressionCalculator.getProgression();

        progressBar.setProgress(progression);
        String style;
        if (progression < 0.33) style = "-fx-accent: red;";
        else if (progression < 1.0) style = "-fx-accent: orange;";
        else style = "-fx-accent: green;";
        progressBar.setStyle(style);

        Label progress = new Label("Project " + a.getProject() + " Progress: " + progressBar.getProgress()*100 + "%");
        progress.setStyle("-fx-font-size: 12px;");
        VBox progressBarLayout = new VBox(progressBar, progress);
        progressBarLayout.setSpacing(5);
        progressBarLayout.setAlignment(Pos.CENTER_LEFT);
        return progressBarLayout;
    }
    public String activityDescription(Activity activity) {
        activityCalculator = new ActivityTimeCalculator(activity);
        return switch (activity.getStatus()) {
            case PLANNED ->
                    "Start Time " + activity.getStartTime().atZone(ZoneId.of("UTC+1")).format(DateTimeFormatter.ofPattern("HH:mm")) + System.lineSeparator() +
                            " Expected End Time " + activity.expectedEndTime().atZone(ZoneId.of("UTC+1")).format(DateTimeFormatter.ofPattern("HH:mm"));
            case EXPIRED -> "Activity " + activity.getTitle() + ", Project: " + activity.getProject() + " expired";
            case COMPLETED ->
                    "Started at " + activity.getStartTime().atZone(ZoneId.of("UTC+1")).format(DateTimeFormatter.ofPattern("HH:mm")) + System.lineSeparator() +
                            "Ended at " + activity.getActualEndTime().atZone(ZoneId.of("UTC+1")).format(DateTimeFormatter.ofPattern("HH:mm")) + System.lineSeparator() +
                            "Actual Duration " + activity.getActualDuration().toMinutes() + System.lineSeparator() +
                            "Estimation difference: " + activityCalculator.estimationDifference().toMinutes();
        };
    }

}
