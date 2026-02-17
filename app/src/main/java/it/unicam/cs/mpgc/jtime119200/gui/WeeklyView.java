package it.unicam.cs.mpgc.jtime119200.gui;


import it.unicam.cs.mpgc.jtime119200.controllers.EventController;
import it.unicam.cs.mpgc.jtime119200.model.DailyViewModel;
import it.unicam.cs.mpgc.jtime119200.model.WeeklyViewModel;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

/**
 * Main week view layout. Responsible for navigation and rendering day columns.
 */
public class WeeklyView extends BorderPane {

    private WeeklyViewModel viewModel;

    private Label title;
    private final EventController controller;


    public WeeklyView(WeeklyViewModel viewModel, EventController controller) {
        this.viewModel = viewModel;
        this.controller = controller;
        this.setTop(createTop());
        this.setCenter(createCenter(viewModel));
    }

    public GridPane createTop() {
        GridPane top = new GridPane();
        top.getStyleClass().add("weeklyView-top");

        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(33.33);
        column1.setHalignment(HPos.CENTER);

        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(33.33);
        column2.setHalignment(HPos.CENTER);

        ColumnConstraints column3 = new ColumnConstraints();
        column3.setPercentWidth(33.33);
        column3.setHalignment(HPos.CENTER);

        top.getColumnConstraints().addAll(column1, column2, column3);

        Button prevWeek = new Button(viewModel.prevButtonString());
        prevWeek.setOnAction(e -> controller.onPrevWeek());
        prevWeek.getStyleClass().add("weeklyView-prevWeek");

        this.title = new Label(viewModel.getWeekLabel());
        title.getStyleClass().add("weeklyView-title");

        Button nextWeek = new Button(viewModel.nextButtonString());
        nextWeek.setOnAction(e -> controller.onNextWeek());
        nextWeek.getStyleClass().add("weeklyView-nextWeek");

        top.add(prevWeek, 0, 0);
        top.add(title, 1, 0);
        top.add(nextWeek, 2, 0);

        return top;
    }


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


    public void refresh(WeeklyViewModel viewModel) {
        this.viewModel = viewModel;
        this.title.setText(viewModel.getWeekLabel());
        this.setCenter(createCenter(viewModel));
    }
}