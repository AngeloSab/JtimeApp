package it.unicam.cs.mpgc.jtime119200.gui;

import it.unicam.cs.mpgc.jtime119200.controllers.EventController;
import it.unicam.cs.mpgc.jtime119200.gui.form.CompleteActivityForm;
import it.unicam.cs.mpgc.jtime119200.model.ActivityViewModel;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

/**
 * Graphical representation of a single activity within the user interface.
 * This class is responsible for rendering the visual component associated
 * with an activity and delegating user interactions to the {@link EventController}.
 * It follows an MVVM-oriented structure:
 * the {@link ActivityViewModel} provides presentation data,
 * while this class manages layout, styling, and interaction handling.
 */
public class ActivityView extends StackPane {

    private final ActivityViewModel viewModel;
    private final EventController controller;
    private final StackPane mainPane;
    private Button checkButton;

    /**
     * Creates a new ActivityView bound to the given view model
     * and controller.
     *
     * @param viewModel the view model containing presentation data
     * @param controller the controller handling user actions
     */
    public ActivityView(ActivityViewModel viewModel, EventController controller) {

        this.viewModel = viewModel;
        this.controller = controller;

        mainPane = new StackPane();
        this.getChildren().add(mainPane);

        mainPane.getStyleClass().add("activityView");

        initialize();
    }

    /**
     * Initializes the graphical components, applies styling,
     * installs tooltips, and configures interactive elements.
     */
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

            checkButton = createCheckButton();
            StackPane.setAlignment(checkButton, Pos.BOTTOM_LEFT);

            mainPane.getChildren().addAll(tripleDot, checkButton);

            mainPane.setOnMouseClicked(event -> {
                event.consume();
                controller.onSelect(this);
            });
        }
    }

    /**
     * Creates the context menu button used to edit or remove
     * the activity when it is in planned state.
     *
     * @return the configured button instance
     */
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

    /**
     * Creates the button used to mark the activity as completed.
     *
     * @return the configured completion button
     */
    public Button createCheckButton() {
        checkButton = new Button("✅");
        checkButton.setVisible(false);
        checkButton.getStyleClass().add("activityView-check");
        checkButton.setOnAction(event -> controller.onComplete(this));
        return checkButton;
    }

    /**
     * Replaces the current view with the completion form.
     *
     * @param form the form used to complete the activity
     */
    public void showCompleteForm(CompleteActivityForm form) {
        this.getChildren().clear();
        this.getChildren().add(form);
    }

    /**
     * Restores the original activity view after the completion form
     * has been hidden.
     */
    public  void hideCompleteForm() {
        this.getChildren().clear();
        this.getChildren().add(mainPane);
    }

    /**
     * Applies CSS style classes based on the current status
     * of the underlying activity.
     */
    private void applyActivityStyle () {
        switch (viewModel.getActivity().getStatus()) {
            case PLANNED -> this.getStyleClass().add("activityView-planned");
            case COMPLETED -> this.getStyleClass().add("activityView-completed");
            case EXPIRED -> this.getStyleClass().add("activityView-expired");
        }
    }

    /**
     * Returns the associated view model.
     *
     * @return the ActivityViewModel instance
     */
    public ActivityViewModel getViewModel() {
        return viewModel;
    }

    /**
     * Hides the completion check button.
     */
    public void hideCheckButton() {
        checkButton.setVisible(false);
    }

    /**
     * Shows the completion check button.
     */
    public void showCheckButton() {
        checkButton.setVisible(true);
    }
}