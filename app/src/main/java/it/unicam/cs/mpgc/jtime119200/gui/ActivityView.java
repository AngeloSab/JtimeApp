package it.unicam.cs.mpgc.jtime119200.gui;

import it.unicam.cs.mpgc.jtime119200.application.CreateActivityController;
import it.unicam.cs.mpgc.jtime119200.model.ActivityViewModel;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.function.Consumer;

/**
 * Small card-like component used to display an activity in the day column.
 */
public class ActivityView extends VBox {

    private final ActivityViewModel viewModel;
    Consumer<ActivityViewModel> onEdit;

    public ActivityView(ActivityViewModel viewModel, Consumer<ActivityViewModel> onEdit) {
        this.onEdit = onEdit;
        this.viewModel = viewModel;

        this.setMinHeight(95);
        this.setSpacing(5);
        this.setAlignment(Pos.CENTER);
        this.setStyle(viewModel.getBorderStyle());
        this.setMaxHeight(Double.MAX_VALUE);
        Tooltip actTip = new Tooltip(viewModel.getTooltipText());
        actTip.setShowDelay(new Duration(500));
        actTip.setHideDelay(new Duration(1));

        Tooltip.install(this, actTip);
        Label title = new Label(viewModel.getTitle() + " - (" + viewModel.getProject()+")");
        MenuButton options = createTripleDot();

        this.getChildren().addAll(title, options);
    }

    private MenuButton createTripleDot() {
        MenuButton optionsButton = new MenuButton("⋮");
        optionsButton.setFocusTraversable(false);

        optionsButton.setStyle("""
        -fx-background-color: transparent;
        -fx-font-size: 16;
        -fx-padding: 0;
    """);

        MenuItem editItem = new MenuItem("Edit Activity");
        if (viewModel.isClickable()) {
            editItem.setOnAction(event -> {onEdit.accept(viewModel); });
        }
        MenuItem removeItem = new MenuItem("Remove Activity");
        removeItem.setStyle("-fx-text-fill: red;");

        optionsButton.getItems().addAll(editItem, removeItem);

        optionsButton.getStyleClass().add("no-arrow");

        return optionsButton;
    }

}