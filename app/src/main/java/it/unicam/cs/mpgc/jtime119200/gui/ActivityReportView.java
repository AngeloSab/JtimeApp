package it.unicam.cs.mpgc.jtime119200.gui;

import it.unicam.cs.mpgc.jtime119200.model.ActivityReportViewModel;
import javafx.scene.layout.VBox;

/**
 * UI component that displays a completed activity summary.
 */
public class ActivityReportView extends VBox {

    private final ActivityReportViewModel viewModel;

    public ActivityReportView(ActivityReportViewModel viewModel, Runnable onClose) {
        this.viewModel = viewModel;
        // TODO: build report layout and bind close action
    }

    public ActivityReportViewModel getViewModel() {
        return viewModel;
    }
}
