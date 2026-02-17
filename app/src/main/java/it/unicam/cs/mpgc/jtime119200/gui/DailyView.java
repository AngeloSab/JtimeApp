package it.unicam.cs.mpgc.jtime119200.gui;

import it.unicam.cs.mpgc.jtime119200.controllers.EventController;
import it.unicam.cs.mpgc.jtime119200.model.ActivityViewModel;
import it.unicam.cs.mpgc.jtime119200.model.DailyViewModel;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.time.LocalDate;

public class DailyView extends VBox {
    private final DailyViewModel viewModel;
    //private final Consumer<LocalDate> onDayHeaderClicked;
    private final EventController controller;



    public DailyView(DailyViewModel viewModel, EventController controller){

        this.viewModel = viewModel;
        this.controller = controller;
        this.getStyleClass().add("dailyView");
        this.setFillWidth(true);

        VBox header = createDayHeader();

        VBox activities = new VBox();
        activities.getStyleClass().add("dailyView-activities");

        for (ActivityViewModel avm : viewModel.getActivities()) {
            ActivityView avView = new ActivityView(avm, controller);
            activities.getChildren().add(avView);
        }

        ScrollPane scroll = new ScrollPane(activities);
        scroll.getStyleClass().add("dailyView-scroll");
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
        if (!viewModel.getDate().isBefore(LocalDate.now()))
            header.setOnMouseClicked(e -> controller.onDayClicked(viewModel.getDate()));

        Tooltip.install(header, new Tooltip(viewModel.tooltipString()));
        return header;
    }

}