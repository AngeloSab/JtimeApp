package it.unicam.cs.mpgc.jtime119200.gui;

import it.unicam.cs.mpgc.jtime119200.controllers.reportHandler.ReportController;
import it.unicam.cs.mpgc.jtime119200.domain.Activity;
import it.unicam.cs.mpgc.jtime119200.model.ActivityViewModel;
import it.unicam.cs.mpgc.jtime119200.model.ProjectReportViewModel;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Graphical view responsible for displaying a detailed report of a project.
 * This class builds a dedicated window containing:
 * project summary information, statistical overview,
 * a pie chart representing activity distribution,
 * a detailed activity table, and management actions.
 * It relies on {@link ProjectReportViewModel} to provide
 * all presentation-ready data and delegates business operations
 * such as project deletion to the {@link ReportController}.
 */
public class ProjectReportView {

    private final ReportController reportController;
    private final Stage stage = new Stage();
    private final ProjectReportViewModel projectReportViewModel;

    /**
     * Creates and displays a new project report window.
     *
     * @param reportController the controller responsible for report-related actions
     */
    public ProjectReportView(ReportController reportController) {
        this.reportController = reportController;
        projectReportViewModel = new ProjectReportViewModel(reportController.getProject());
        stage.setTitle(projectReportViewModel.getTitle());
        Scene scene = new Scene(createRoot());
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Creates the root container of the report view,
     * assembling all UI sections.
     *
     * @return the root layout container
     */
    private VBox createRoot() {

        VBox root = new VBox(20);
        root.getStyleClass().add("projectReport-root");

        root.getChildren().addAll(createHeader(), createTopSection(),
                createSeparator(), createActivityList(), createBottomButtons());

        return root;
    }

    /**
     * Creates the header section displaying project name and status.
     *
     * @return the header container
     */
    private VBox createHeader() {
        VBox header = new VBox(5);

        Label title = new Label(projectReportViewModel.projectName());
        title.getStyleClass().add("ProjectReportView-title");

        Label status = new Label(projectReportViewModel.projectStatus());
        status.getStyleClass().add("ProjectReportView-status");

        header.getChildren().addAll(title, status);
        return header;
    }

    /**
     * Creates the top section containing the overview box
     * and the activity distribution pie chart.
     *
     * @return the horizontal container
     */
    private HBox createTopSection() {
        HBox container = new HBox(40);

        VBox overview = createOverviewBox();
        VBox chart = createPieChart();

        container.getChildren().addAll(overview, chart);
        return container;
    }

    /**
     * Creates the overview box showing aggregated
     * duration statistics and progression.
     *
     * @return the overview container
     */
    private VBox createOverviewBox() {
        VBox box = new VBox(8);

        Label sectionTitle = new Label("Project Overview");
        sectionTitle.getStyleClass().add("section-title");

        box.getChildren().addAll(
                sectionTitle,
                new Label("Total Expected Duration: " + projectReportViewModel.getTotalExpected()),
                new Label("Total Actual Duration: " + projectReportViewModel.getTotalActual()),
                new Label("Duration Difference: " + projectReportViewModel.getTotalDifference()),
                new Label("Average Expected Duration: " + projectReportViewModel.getAverageExpected()),
                new Label("Average Actual Duration: " + projectReportViewModel.getAverageActual()),
                new Label("Average Duration Difference: " + projectReportViewModel.getAverageDifference()),
                createProgressBar()
        );

        return box;
    }

    /**
     * Creates a pie chart representing the distribution
     * of activities by status.
     *
     * @return the container holding the pie chart
     */
    private VBox createPieChart() {
        VBox pieBar = new VBox();

        PieChart divisionPie = new PieChart();
        if (projectReportViewModel.getCompleted() > 0)
            divisionPie.getData().add(new PieChart.Data("Completed", projectReportViewModel.getCompleted()));
        if (projectReportViewModel.getPlanned() > 0)
            divisionPie.getData().add(new PieChart.Data("Planned", projectReportViewModel.getPlanned()));
        if (projectReportViewModel.getExpired() > 0)
            divisionPie.getData().add(new PieChart.Data("Expired", projectReportViewModel.getExpired()));
        divisionPie.getStyleClass().add("ProjectReportView-piePie");

        pieBar.getChildren().addAll(divisionPie);

        return pieBar;
    }

    /**
     * Creates a separator used to visually divide sections.
     *
     * @return a Separator instance
     */
    private Separator createSeparator() {
        return new Separator();
    }

    /**
     * Creates the activity list section, including
     * the activity table wrapped in a scroll pane.
     *
     * @return the activity list container
     */
    private VBox createActivityList() {

        VBox container = new VBox(10);

        Label title = new Label(projectReportViewModel.activityListLabel());
        title.getStyleClass().add("projectReport-activityList");

        GridPane table = createActivityTable();

        ScrollPane scroll = new ScrollPane(table);
        scroll.setFitToWidth(true);
        scroll.setPrefHeight(250);

        container.getChildren().addAll(title, scroll);
        return container;
    }

    /**
     * Creates the grid-based table displaying
     * detailed information for each activity.
     *
     * @return the populated GridPane
     */
    private GridPane createActivityTable() {

        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(8);
        grid.getStyleClass().add("activity-table");

        String[] headers = projectReportViewModel.headersStrings();

        for (int col = 0; col < headers.length; col++) {
            Label header = new Label(headers[col]);
            header.getStyleClass().add("activity-table-header");
            grid.add(header, col, 0);
        }

        int rowIndex = 1;

        for (Activity activity : projectReportViewModel.getActivities()) {
            ActivityViewModel vm = new ActivityViewModel(activity);

            grid.add(new Label(vm.getDate().toString()), 0, rowIndex);
            grid.add(new Label(vm.getTitle()), 1, rowIndex);
            grid.add(new Label(vm.getStatusSymbol()), 2, rowIndex);
            grid.add(new Label(vm.getStartTime()), 3, rowIndex);
            grid.add(new Label(vm.getExpectedEnd()), 4, rowIndex);
            grid.add(new Label(vm.getActualEndOrDash()), 5, rowIndex);
            grid.add(new Label(vm.getExpectedDurationMinutes()), 6, rowIndex);
            grid.add(new Label(vm.getActualDurationOrDash()), 7, rowIndex);
            grid.add(new Label(vm.getDurationDifference()), 8, rowIndex);

            rowIndex++;
        }

        return grid;
    }

    /**
     * Creates the progression bar section.
     *
     * @return the container holding the progress bar
     */
    private VBox createProgressBar() {
        VBox boxBar = new VBox();

        Label progressLabel = new Label(projectReportViewModel.progressBarTitle());
        progressLabel.getStyleClass().add("ProjectReportView-progressLabel");
        ProgressBar progressBar = new ProgressBar(projectReportViewModel.progressBarValue());
        progressBar.getStyleClass().add("ProjectReportView-progressBar");
        boxBar.getChildren().addAll(progressLabel, progressBar);

        return boxBar;
    }

    /**
     * Creates the bottom section containing
     * the report management buttons.
     *
     * @return the button container
     */
    private HBox createBottomButtons() {

        HBox buttons = new HBox(20);

        Button close = new Button("Close Report");
        close.getStyleClass().add("ProjectReportView-closeButton");
        close.setOnAction(e -> stage.close());

        Button closeAndDelete = new Button("Close & Delete Project");
        closeAndDelete.getStyleClass().add("ProjectReportView-closeAndDeleteButton");
        closeAndDelete.setOnAction(e -> {
            if (projectReportViewModel.getPlanned() == 0) {
                reportController.deleteProject();
                stage.close();
            } else {
                showError();
            }
        });

        buttons.getChildren().addAll(close, closeAndDelete);
        return buttons;
    }

    /**
     * Displays an error alert when deletion
     * of the project is not allowed.
     */
    private void showError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Delete Error");
        alert.setContentText("Impossible to delete an uncompleted project");
        alert.showAndWait();
    }

}