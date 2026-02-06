package it.unicam.cs.mpgc.jtime119200.gui;

import it.unicam.cs.mpgc.jtime119200.model.ActivityViewModel;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;

import java.util.function.Consumer;

public class ActivityCellView extends VBox {

    public ActivityCellView(ActivityViewModel vm, Consumer<ActivityViewModel> onClick) {
        setSpacing(3);
        setMinHeight(75);
        setStyle(vm.getBorderStyle());

        Label title = new Label(vm.getTitle());
        Label time = new Label(vm.getTimeLabel());

        getChildren().addAll(title, time);

        Tooltip tooltip = new Tooltip(vm.getTooltipText());
        Tooltip.install(this, tooltip);

        if (vm.isClickable()) {
            setOnMouseClicked(e -> onClick.accept(vm));
        }
    }
}

