package it.unicam.cs.mpgc.jtime119200.gui;

import it.unicam.cs.mpgc.jtime119200.model.ActivityViewModel;
import it.unicam.cs.mpgc.jtime119200.model.DailyViewModel;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.function.Consumer;

/**
 * Column that renders a single day header and its activities.
 */
public class DailyView extends VBox {

    private final DailyViewModel viewModel;
    private final Consumer<LocalDate> onDayHeaderClicked;

    public DailyView(DailyViewModel viewModel,
                     Consumer<ActivityViewModel> onActivitySelected,
                     Consumer<LocalDate> onDayHeaderClicked) {
        this.viewModel = viewModel;
        this.onDayHeaderClicked = onDayHeaderClicked;

        VBox column = createDayHeader();
        VBox activities = new VBox(3);
        for (ActivityViewModel avm : viewModel.getActivities()){
            VBox activity = new VBox(5);
            activity.setAlignment(Pos.BASELINE_CENTER);
            activity.setStyle("-fx-border-color: black;");
            ActivityView avView = new ActivityView(avm, onActivitySelected);
            activity.getChildren().add(avView);
            avView.setOnMouseClicked( e -> onActivitySelected.accept(avm));
            activities.getChildren().add(avView);
        }

        ScrollPane scroll = new ScrollPane(activities);
        scroll.setFitToWidth(true);
        scroll.setFitToHeight(true);

        VBox.setVgrow(scroll, Priority.ALWAYS);

        column.getChildren().addAll(activities);
        this.getChildren().add(column);
    }

    private VBox createDayHeader() {
        VBox column = new VBox(5);
        column.setAlignment(Pos.TOP_CENTER);
        column.setPrefWidth(150);
        column.setPrefHeight(680);
        column.setMaxHeight(Double.MAX_VALUE);
        column.setMaxWidth(Double.MAX_VALUE);
        column.setStyle("-fx-border-color: black;");

        VBox header = new VBox();
        header.setAlignment(Pos.CENTER);
        header.setMinHeight(80);
        header.setPrefHeight(80);
        header.setMaxWidth(Double.MAX_VALUE);
        header.setStyle("-fx-border-color: black;");

        Label dayLabel = new Label(viewModel.getDateLabel());
        dayLabel.setStyle("-fx-font-weight: bold;");

        header.setOnMouseClicked(e -> onDayHeaderClicked.accept(viewModel.getDate()));
        Tooltip.install(header, new Tooltip("Click to create a new Activity"));

        header.getChildren().add(dayLabel);
        column.getChildren().add(header);
        return column;
    }
}