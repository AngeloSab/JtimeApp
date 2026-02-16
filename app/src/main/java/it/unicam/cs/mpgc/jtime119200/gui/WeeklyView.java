package it.unicam.cs.mpgc.jtime119200.gui;


import it.unicam.cs.mpgc.jtime119200.model.ActivityViewModel;
import it.unicam.cs.mpgc.jtime119200.model.DailyViewModel;
import it.unicam.cs.mpgc.jtime119200.model.WeeklyViewModel;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.time.LocalDate;
import java.util.function.Consumer;

/**
 * Main week view layout. Responsible for navigation and rendering day columns.
 */
public class WeeklyView extends BorderPane {

    private WeeklyViewModel viewModel;
    private final Consumer<ActivityViewModel> onEdit;
    private final Consumer<ActivityViewModel> onRemove;
    private final Consumer<ActivityView> onSelect;
    private final Consumer<ActivityView>onComplete;
    private final Consumer<LocalDate> onDayHeaderClicked;
    private final Runnable onPrevWeek;
    private final Runnable onNextWeek;
    private Label title;


    public WeeklyView(WeeklyViewModel viewModel,
                      Consumer<ActivityViewModel> onEdit,
                      Consumer<ActivityViewModel> onRemove,
                      Consumer<ActivityView> onSelect,
                      Consumer<ActivityView> onComplete,
                      Consumer<LocalDate> onDayHeaderClicked,
                      Runnable onPrevWeek,
                      Runnable onNextWeek) {
        this.viewModel = viewModel;
        this.onEdit = onEdit;
        this.onRemove = onRemove;
        this.onSelect = onSelect;
        this.onComplete = onComplete;
        this.onDayHeaderClicked = onDayHeaderClicked;
        this.onPrevWeek = onPrevWeek;
        this.onNextWeek = onNextWeek;
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
        prevWeek.setOnAction(e -> onPrevWeek.run());
        prevWeek.getStyleClass().add("weeklyView-prevWeek");

        this.title = new Label(viewModel.getWeekLabel());
        title.getStyleClass().add("weeklyView-title");

        Button nextWeek = new Button(viewModel.nextButtonString());
        nextWeek.setOnAction(e -> onNextWeek.run());
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
            DailyView dailyView = new DailyView(dvm, onRemove,  onEdit, onSelect, onComplete, onDayHeaderClicked);
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