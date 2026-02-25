package it.unicam.cs.mpgc.jtime119200.gui;

import it.unicam.cs.mpgc.jtime119200.controllers.EventController;
import it.unicam.cs.mpgc.jtime119200.domain.Project;
import it.unicam.cs.mpgc.jtime119200.model.DailyViewModel;
import it.unicam.cs.mpgc.jtime119200.model.WeeklyViewModel;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

/**
 * Main week view layout.
 * This class represents the graphical component that displays
 * the weekly calendar view with navigation controls and day columns.
 * It is responsible for:
 * Showing the current week label
 * Handling week navigation
 * Rendering daily columns
 * Providing access to project reports
 */
public class WeeklyView extends BorderPane {

    private WeeklyViewModel viewModel;
    private Label title;
    private final EventController controller;

    /**
     * Creates a new WeeklyView.
     *
     * @param viewModel  the model containing week data
     * @param controller the controller managing interactions
     */
    public WeeklyView(WeeklyViewModel viewModel, EventController controller) {
        this.viewModel = viewModel;
        this.controller = controller;
        this.setTop(createTop());
        this.setCenter(createCenter(viewModel));
    }

    /**
     * Creates the top section of the layout.
     * It contains navigation buttons, the week title,
     * and the project report selection area.
     *
     * @return the configured GridPane for the top area
     */
    public GridPane createTop() {
        GridPane top = new GridPane();
        top.getStyleClass().add("weeklyView-top");

        for (int i = 0; i < 5; i++) {
            ColumnConstraints col = new ColumnConstraints();
            col.setPercentWidth(20);
            col.setHalignment(HPos.CENTER);
            top.getColumnConstraints().add(col);
        }

        Button prevWeek = new Button(viewModel.prevButtonString());
        prevWeek.setOnAction(e -> controller.onPrevWeek());
        prevWeek.getStyleClass().add("weeklyView-prevWeek");

        this.title = new Label(viewModel.getWeekLabel());
        title.getStyleClass().add("weeklyView-title");

        Button nextWeek = new Button(viewModel.nextButtonString());
        nextWeek.setOnAction(e -> controller.onNextWeek());
        nextWeek.getStyleClass().add("weeklyView-nextWeek");

        VBox reportBox = createReportBox();

        top.add(prevWeek, 1, 0);
        top.add(title, 2, 0);
        top.add(nextWeek, 3, 0);
        top.add(reportBox, 4, 0);

        return top;
    }

    /**
     * Creates the project report selection box.
     * Allows the user to select a project and generate a report.
     *
     * @return the VBox containing report controls
     */
    private VBox createReportBox() {
        VBox reportBox = new VBox();
        reportBox.getStyleClass().add("weeklyView-reportBox");

        Label reportBoxLabel = new Label(viewModel.reportString());
        reportBoxLabel.getStyleClass().add("weeklyView-reportBoxLabel");

        ComboBox<Project> reportProjectList = new ComboBox<>();
        for (Project p : controller.getProjectList()) {
            reportProjectList.getItems().add(p);
        }

        reportProjectList.getStyleClass().add("weeklyView-reportProjectList");
        reportBox.getChildren().addAll(reportBoxLabel, reportProjectList);

        reportProjectList.setOnAction(e ->
                controller.projectReport(reportProjectList.getValue()));

        return reportBox;
    }

    /**
     * Creates the center section of the layout.
     * It contains one DailyView for each day of the week.
     *
     * @param viewModel the model containing daily data
     * @return the HBox containing all daily views
     */
    public HBox createCenter(WeeklyViewModel viewModel) {
        HBox center = new HBox(10);
        center.setAlignment(Pos.CENTER);
        center.setFillHeight(true);

        for (DailyViewModel dvm : viewModel.getDays()) {
            DailyView dailyView = new DailyView(dvm, controller);
            HBox.setHgrow(dailyView, Priority.ALWAYS);
            dailyView.setMaxWidth(Double.MAX_VALUE);
            center.getChildren().add(dailyView);
        }
        return center;
    }

    /**
     * Refreshes the view using a new WeeklyViewModel.
     * Updates the week label and recreates the daily views.
     *
     * @param viewModel the updated weekly data
     */
    public void refresh(WeeklyViewModel viewModel) {
        this.viewModel = viewModel;
        this.title.setText(viewModel.getWeekLabel());
        this.setCenter(createCenter(viewModel));
    }
}