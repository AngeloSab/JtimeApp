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

/**
 * Graphical component representing a single day within the weekly interface.
 * This view is responsible for rendering the day header and the list
 * of activities associated with that date. It relies on the
 * {@link DailyViewModel} for presentation data and delegates user
 * interactions to the {@link EventController}.
 * The component displays all activities vertically and enables
 * interaction with future or current days.
 */
public class DailyView extends VBox {
    private final DailyViewModel viewModel;
    //private final Consumer<LocalDate> onDayHeaderClicked;
    private final EventController controller;

    /**
     * Creates a new DailyView bound to the specified view model
     * and controller.
     *
     * @param viewModel the view model containing date and activity data
     * @param controller the controller responsible for handling user actions
     */
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

    /**
     * Creates the header section of the day view.
     * The header displays the formatted date and allows interaction
     * if the represented date is not in the past. A tooltip is also
     * installed to provide additional contextual information.
     *
     * @return the configured header container
     */
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