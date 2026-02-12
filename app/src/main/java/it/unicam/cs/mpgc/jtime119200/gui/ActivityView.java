package it.unicam.cs.mpgc.jtime119200.gui;

import it.unicam.cs.mpgc.jtime119200.model.ActivityViewModel;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.function.Consumer;

/**
 * Small card-like component used to display an activity in the day column.
 */
public class ActivityView extends StackPane {

    private final ActivityViewModel viewModel;
    private final Consumer<ActivityViewModel> onEdit;
    private final Consumer<ActivityViewModel> onRemove;
    Tooltip actTip;

    public ActivityView(ActivityViewModel viewModel,
                        Consumer<ActivityViewModel> onEdit,
                        Consumer<ActivityViewModel> onRemove) {

        this.viewModel = viewModel;
        this.onEdit = onEdit;
        this.onRemove = onRemove;

        this.getStyleClass().add("activityView");
        applyActivityStyle();

        actTip = new Tooltip(viewModel.getTooltipText());
        actTip.setShowDelay(new Duration(250));
        Tooltip.install(this, actTip);

        VBox content = new VBox();
        content.setFillWidth(true);

        Label title = new Label(viewModel.getDescription());
        title.getStyleClass().add("activity-title");

        content.getChildren().add(title);

        Button tripleDot = createTripleDot();

        StackPane.setAlignment(tripleDot, Pos.TOP_RIGHT);

        this.getChildren().addAll(content, tripleDot);
    }


    private Button createTripleDot() {

        Button tripleDot = new Button("⋮");
        tripleDot.getStyleClass().add("activityView-tripleDot");

        MenuItem editItem = new MenuItem("Edit Activity");
        if (viewModel.isClickable())
            editItem.setOnAction(event -> onEdit.accept(viewModel));

        MenuItem removeItem = new MenuItem("Remove Activity");
        if (viewModel.isClickable())
            removeItem.setOnAction(event -> onRemove.accept(viewModel));

        removeItem.getStyleClass().add("activity-remove-item");

        ContextMenu contextMenu = new ContextMenu(editItem, removeItem);

        tripleDot.setOnAction(e ->
                contextMenu.show(tripleDot, Side.BOTTOM, 0, 0));

        return tripleDot;
    }


    private void applyActivityStyle() {
        switch (viewModel.getActivity().getStatus()) {
            case PLANNED -> this.getStyleClass().add("activityView-planned");
            case COMPLETED -> this.getStyleClass().add("activityView-completed");
            case EXPIRED -> this.getStyleClass().add("activityView-expired");
        }
    }
}
