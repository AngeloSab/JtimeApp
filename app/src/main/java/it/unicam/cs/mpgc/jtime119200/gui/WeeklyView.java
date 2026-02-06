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

import java.time.LocalDate;
import java.util.function.Consumer;

public class WeeklyView extends BorderPane {

    private WeeklyViewModel viewModel;

    private final Label titleLabel = new Label();
    private final HBox weekContainer = new HBox(5);

    private final Consumer<ActivityViewModel> onActivitySelected;
    private final Consumer<LocalDate> onDayHeaderClicked;

    public WeeklyView(WeeklyViewModel vm, Consumer<ActivityViewModel> onActivitySelected,
            Consumer<LocalDate> onDayHeaderClicked, Runnable onPrevWeek, Runnable onNextWeek) {
        this.viewModel = vm;
        this.onActivitySelected = onActivitySelected;
        this.onDayHeaderClicked = onDayHeaderClicked;

        setPadding(new Insets(10));

        // ---------- TOP (navigation) ----------
        Button prev = new Button("⟨");
        Button next = new Button("⟩");

        prev.setOnAction(e -> onPrevWeek.run());
        next.setOnAction(e -> onNextWeek.run());

        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        HBox navigation = new HBox(15, prev, titleLabel, next);
        navigation.setAlignment(Pos.CENTER);

        setTop(navigation);

        // ---------- CENTER (week) ----------
        weekContainer.setAlignment(Pos.TOP_CENTER);
        setCenter(weekContainer);

        refresh();
    }

    public void setViewModel(WeeklyViewModel viewModel) {
        this.viewModel = viewModel;
        refresh();
    }

    private void refresh() {
        // titolo
        titleLabel.setText(viewModel.getWeekLabel());

        // colonne
        weekContainer.getChildren().clear();

        for (DailyViewModel dayVM : viewModel.getDays()) {
            DayColumnView column = new DayColumnView(dayVM, onActivitySelected, onDayHeaderClicked);
            HBox.setHgrow(column, Priority.ALWAYS);
            column.setMaxWidth(Double.MAX_VALUE);
            weekContainer.getChildren().add(column);
        }
    }
}
