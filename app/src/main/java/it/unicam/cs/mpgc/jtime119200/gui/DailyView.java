package it.unicam.cs.mpgc.jtime119200.gui;

import it.unicam.cs.mpgc.jtime119200.model.ActivityViewModel;
import it.unicam.cs.mpgc.jtime119200.model.DailyViewModel;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.function.Consumer;

public class DailyView extends VBox {
    private final DailyViewModel viewModel;
    private final Consumer<LocalDate> onDayHeaderClicked;



    public DailyView(DailyViewModel viewModel,
                     Consumer<ActivityViewModel> onRemove,
                     Consumer<ActivityViewModel> onEdit,
                     Consumer<ActivityView> onSelect,
                     Consumer<ActivityView> onComplete,
                     Consumer<LocalDate> onDayHeaderClicked) {

        this.viewModel = viewModel;
        this.onDayHeaderClicked = onDayHeaderClicked;

        this.getStyleClass().add("dailyView");
        this.setFillWidth(true);

        VBox header = createDayHeader();

        VBox activities = new VBox();
        activities.getStyleClass().add("dailyView-activities");

        for (ActivityViewModel avm : viewModel.getActivities()) {
            ActivityView avView = new ActivityView(avm, onEdit, onRemove, onSelect, onComplete);
            activities.getChildren().add(avView);
        }

        // SCROLL
        ScrollPane scroll = new ScrollPane(activities);
        scroll.setFitToWidth(true);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        VBox.setVgrow(scroll, Priority.ALWAYS);

        this.getChildren().addAll(header, scroll);
    }



    private VBox createDayHeader() {

        VBox header = new VBox();
        header.getStyleClass().add("dailyView-header");

        Label dailyViewTitle = new Label(viewModel.getDateLabel());
        dailyViewTitle.getStyleClass().add("dailyView-title");

        header.getChildren().add(dailyViewTitle);
        if (!viewModel.getDate().isBefore(LocalDate.now())) {
            header.setOnMouseClicked(e ->
                    onDayHeaderClicked.accept(viewModel.getDate())
            );

            Tooltip.install(header, new Tooltip(viewModel.tooltipString()));
        }
        return header;
    }

}