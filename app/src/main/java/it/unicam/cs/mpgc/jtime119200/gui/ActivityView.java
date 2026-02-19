package it.unicam.cs.mpgc.jtime119200.gui;

import it.unicam.cs.mpgc.jtime119200.controllers.EventController;
import it.unicam.cs.mpgc.jtime119200.gui.form.CompleteActivityForm;
import it.unicam.cs.mpgc.jtime119200.model.ActivityViewModel;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class ActivityView extends StackPane {

    private final ActivityViewModel viewModel;
    private final EventController controller;
    private final StackPane mainPane;
    private Button checkButton;

    public ActivityView(ActivityViewModel viewModel, EventController controller) {

        this.viewModel = viewModel;
        this.controller = controller;

        mainPane = new StackPane();
        this.getChildren().add(mainPane);

        mainPane.getStyleClass().add("activityView");

        initialize();
    }

    private void initialize() {
        applyActivityStyle();

        Tooltip actTip = new Tooltip(viewModel.getTooltipText());
        actTip.setShowDelay(new Duration(250));
        Tooltip.install(this, actTip);

        Label title = new Label(viewModel.getDescription());
        title.getStyleClass().add("activity-title");
        StackPane.setAlignment(title, Pos.TOP_LEFT);

        mainPane.getChildren().add(title);


        if (viewModel.isPlanned()) {
            Button tripleDot = createTripleDot();
            StackPane.setAlignment(tripleDot, Pos.TOP_RIGHT);

            checkButton = createCheckButton();   // ← USO IL CAMPO
            StackPane.setAlignment(checkButton, Pos.BOTTOM_LEFT);

            mainPane.getChildren().addAll(tripleDot, checkButton);

            mainPane.setOnMouseClicked(event -> {
                event.consume();
                controller.onSelect(this);
            });
        }

    }

    private Button createTripleDot() {

        Button tripleDot = new Button("⋮");
        tripleDot.getStyleClass().add("activityView-tripleDot");

        MenuItem editItem = new MenuItem("Edit Activity");
        if (viewModel.isPlanned())
            editItem.setOnAction(event -> controller.onEdit(viewModel));

        MenuItem removeItem = new MenuItem("Remove Activity");
        if (viewModel.isPlanned())
            removeItem.setOnAction(event -> controller.onRemove(viewModel));

        removeItem.getStyleClass().add("activity-remove-item");

        ContextMenu contextMenu = new ContextMenu(editItem, removeItem);

        tripleDot.setOnAction(e ->
                contextMenu.show(tripleDot, Side.BOTTOM, 0, 0));

        return tripleDot;
    }

    public Button createCheckButton() {
        checkButton = new Button("✅");
        checkButton.setVisible(false);
        checkButton.getStyleClass().add("activityView-check");
        checkButton.setOnAction(event -> controller.onComplete(this));
        return checkButton;
    }

    public void showCompleteForm(CompleteActivityForm form) {
        this.getChildren().clear();
        this.getChildren().add(form);
    }

    public  void hideCompleteForm() {
        this.getChildren().clear();
        this.getChildren().add(mainPane);
    }

    private void applyActivityStyle () {
        switch (viewModel.getActivity().getStatus()) {
            case PLANNED -> this.getStyleClass().add("activityView-planned");
            case COMPLETED -> this.getStyleClass().add("activityView-completed");
            case EXPIRED -> this.getStyleClass().add("activityView-expired");
        }
    }
    public ActivityViewModel getViewModel() {
        return viewModel;
    }

    public void hideCheckButton() {
        checkButton.setVisible(false);
    }

    public void showCheckButton() {
        checkButton.setVisible(true);
    }
}
