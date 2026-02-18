package it.unicam.cs.mpgc.jtime119200.gui;

import it.unicam.cs.mpgc.jtime119200.controllers.ReportController;
import it.unicam.cs.mpgc.jtime119200.domain.Activity;
import it.unicam.cs.mpgc.jtime119200.model.ProjectReportModel;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ProjectReportView {

    private final ReportController reportController;
    private final Stage stage = new Stage();
    private final ProjectReportModel projectReportModel;



    public ProjectReportView(ReportController reportController) {
        this.reportController = reportController;
        projectReportModel = new ProjectReportModel(reportController.getProject());
        stage.setTitle(projectReportModel.getTitle());
        Scene scene = new Scene(createRoot());
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();

    }

    private VBox createRoot() {

        VBox root = new VBox(20);
        root.getStyleClass().add("projectReport-root");

        root.getChildren().addAll(createHeader(), createTopSection(),
                createSeparator(), createActivityList(), createBottomButtons());

        return root;
    }

    private VBox createHeader() {
        VBox header = new VBox(5);

        Label title = new Label(projectReportModel.projectName());
        title.getStyleClass().add("ProjectReportView-title");

        Label status = new Label(projectReportModel.projectStatus());
        status.getStyleClass().add("ProjectReportView-status");

        header.getChildren().addAll(title, status);
        return header;
    }

    private HBox createTopSection() {
        HBox container = new HBox(40);

        VBox overview = createOverviewBox();
        VBox chart = createPieChart();

        container.getChildren().addAll(overview, chart);
        return container;
    }

    private VBox createOverviewBox() {
        VBox box = new VBox(8);

        Label sectionTitle = new Label("Project Overview");
        sectionTitle.getStyleClass().add("section-title");

        box.getChildren().addAll(
                sectionTitle,
                new Label("Total Expected Duration: " + projectReportModel.getTotalExpected()),
                new Label("Total Actual Duration: " + projectReportModel.getTotalActual()),
                new Label("Duration Difference: " + projectReportModel.getTotalDifference()),
                new Label("Average Expected Duration: " + projectReportModel.getAverageExpected()),
                new Label("Average Actual Duration: " + projectReportModel.getAverageActual()),
                new Label("Average Duration Difference: " + projectReportModel.getAverageDifference()),
                createProgressBar()
        );

        return box;
    }

    private VBox createPieChart() {
        VBox pieBar = new VBox();

        Label pieLabel = new Label(projectReportModel.progressBarTitle());
        pieLabel.getStyleClass().add("ProjectReportView-pieLabel");
        PieChart divisionPie = new PieChart();
        if (projectReportModel.getCompleted() > 0)
            divisionPie.getData().add(new PieChart.Data("Completed", projectReportModel.getCompleted()));
        if (projectReportModel.getPlanned() > 0)
            divisionPie.getData().add(new PieChart.Data("Planned", projectReportModel.getPlanned()));
        if (projectReportModel.getExpired() > 0)
            divisionPie.getData().add(new PieChart.Data("Expired", projectReportModel.getExpired()));
        divisionPie.getStyleClass().add("ProjectReportView-piePie");

        pieBar.getChildren().addAll(pieLabel, divisionPie);

        return pieBar;
    }

    private Separator createSeparator() {
        return new Separator();
    }

    private VBox createActivityList() {

        VBox container = new VBox(10);

        Label title = new Label(projectReportModel.activityListLabel());
        title.getStyleClass().add("projectReport-activityList");

        VBox activitiesBox = new VBox(5);


        for (Activity activity : projectReportModel.getActivities()){
            activitiesBox.getChildren().add(createActivityRow(activity));
        }

        ScrollPane scroll = new ScrollPane(activitiesBox);
        scroll.setFitToWidth(true);
        scroll.setPrefHeight(200);

        container.getChildren().addAll(title, scroll);
        return container;
    }

    private HBox createActivityRow(Activity activity) {

        HBox row = new HBox(20);

        Label name = new Label(activity.getTitle());
        if (activity.isCompleted()) {
            name.setText(name.getText() + " ✔");
        }

        Label expected = new Label(activity.getExpectedDuration().toString());
        Label actual = new Label(
                activity.isCompleted()
                        ? (activity.getActualDuration().toString())
                        : "-"
        );

        row.getChildren().addAll(name, expected, actual);
        return row;
    }

    private VBox createProgressBar() {
        VBox boxBar = new VBox();

        Label progressLabel = new Label(projectReportModel.progressBarTitle());
        progressLabel.getStyleClass().add("ProjectReportView-progressLabel");
        ProgressBar progressBar = new ProgressBar(projectReportModel.progressBarValue());
        progressBar.getStyleClass().add("ProjectReportView-progressBar");
        boxBar.getChildren().addAll(progressLabel, progressBar);

        return boxBar;
    }

    private HBox createBottomButtons() {

        HBox buttons = new HBox(20);

        Button close = new Button("Close Report");
        close.getStyleClass().add("ProjectReportView-closeButton");
        close.setOnAction(e -> stage.close());

        Button closeAndDelete = new Button("Close & Delete Project");
        closeAndDelete.getStyleClass().add("ProjectReportView-closeAndDeleteButton");
        closeAndDelete.setOnAction(e -> {
            reportController.deleteProject();
            stage.close();
        });

        buttons.getChildren().addAll(close, closeAndDelete);
        return buttons;
    }

}
