package it.unicam.cs.mpgc.jtime119200.gui;


import it.unicam.cs.mpgc.jtime119200.model.ActivityViewModel;
import it.unicam.cs.mpgc.jtime119200.model.DailyViewModel;
import it.unicam.cs.mpgc.jtime119200.model.WeeklyViewModel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

import java.time.LocalDate;
import java.util.function.Consumer;

/**
 * Main week view layout. Responsible for navigation and rendering day columns.
 */
public class WeeklyView extends BorderPane {

    private WeeklyViewModel viewModel;
    private final Consumer<ActivityViewModel> onActivitySelected;
    private final Consumer<LocalDate> onDayHeaderClicked;
    private final Runnable onPrevWeek;
    private final Runnable onNextWeek;


    public WeeklyView(WeeklyViewModel viewModel,
                      Consumer<ActivityViewModel> onActivitySelected,
                      Consumer<LocalDate> onDayHeaderClicked,
                      Runnable onPrevWeek,
                      Runnable onNextWeek) {
        this.viewModel = viewModel;
        this.onActivitySelected = onActivitySelected;
        this.onDayHeaderClicked = onDayHeaderClicked;
        this.onPrevWeek = onPrevWeek;
        this.onNextWeek = onNextWeek;
        this.setTop(createTop());
        this.setCenter(createCenter(viewModel));
    }

    public HBox createTop() {
        HBox top = new HBox();
        top.setAlignment(Pos.CENTER);
        top.setMinHeight(60);
        top.setPadding(new Insets(10));

        Button prevWeek = new Button("Previous Week");
        prevWeek.setOnAction(e -> onPrevWeek.run());

        Label title = new Label(viewModel.getWeekLabel());
        title.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");
        title.setAlignment(Pos.CENTER);

        Button nextWeek = new Button("Next Week");
        nextWeek.setOnAction(e -> onNextWeek.run());

        Region spacerLeft = new Region();
        Region spacerRight = new Region();

        HBox.setHgrow(spacerLeft, Priority.ALWAYS);
        HBox.setHgrow(spacerRight, Priority.ALWAYS);

        top.getChildren().addAll(prevWeek, spacerLeft, title, spacerRight, nextWeek);
        return top;
    }

    public HBox createCenter(WeeklyViewModel viewModel) {
        HBox center = new HBox(10);
        center.setAlignment(Pos.CENTER);
        center.setFillHeight(true);

        for (DailyViewModel dvm : viewModel.getDays()) {
            DailyView dailyView = new DailyView(dvm, onActivitySelected, onDayHeaderClicked);
            HBox.setHgrow(dailyView, Priority.ALWAYS); // 👈
            dailyView.setMaxWidth(Double.MAX_VALUE);
            center.getChildren().add(dailyView);
        }
        return center;
    }


    public void refresh(WeeklyViewModel viewModel) {
        this.viewModel = viewModel;
        this.setCenter(createCenter(viewModel));
    }
}