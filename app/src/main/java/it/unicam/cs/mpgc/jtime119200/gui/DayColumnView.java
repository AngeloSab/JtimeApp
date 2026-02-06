package it.unicam.cs.mpgc.jtime119200.gui;

import it.unicam.cs.mpgc.jtime119200.model.ActivityViewModel;
import it.unicam.cs.mpgc.jtime119200.model.DailyViewModel;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.function.Consumer;

public class DayColumnView extends VBox {

    public DayColumnView(DailyViewModel dayVM, Consumer<ActivityViewModel> onActivityClick, Consumer<LocalDate> onDayHeaderClicked) {
        setSpacing(5);
        setStyle("-fx-border-color: black;");
        setMinHeight(400);

        Label header = new Label(dayVM.getDateLabel());
        header.setStyle("-fx-font-weight: bold;-fx-font-color: #0015ff;");
        header.setMinHeight(90);
        header.setAlignment(Pos.CENTER);

        Tooltip.install(header, new Tooltip("Click the date to insert a new activity"));

        header.setOnMouseClicked(e ->
                onDayHeaderClicked.accept(dayVM.getDate())
        );

        VBox activitiesBox = new VBox(5);
        activitiesBox.setAlignment(Pos.TOP_CENTER);
        for (ActivityViewModel activityVM : dayVM.getActivities()) {
            ActivityCellView cell = new ActivityCellView(activityVM, onActivityClick);
            activitiesBox.getChildren().add(cell);
        }
        this.getChildren().addAll(header, activitiesBox);
    }
}

