package it.unicam.cs.mpgc.jtime119200.controllers;

import it.unicam.cs.mpgc.jtime119200.gui.ActivityView;
import it.unicam.cs.mpgc.jtime119200.gui.WeeklyView;
import it.unicam.cs.mpgc.jtime119200.gui.form.CompleteActivityForm;
import it.unicam.cs.mpgc.jtime119200.model.WeeklyViewModel;

import java.time.Duration;

public class CompleteActivityController {

    private final ActivityView view;
    private final WeeklyViewModel viewModel;

    public CompleteActivityController(ActivityView view, WeeklyViewModel weeklyView) {
        this.view = view;
        this.viewModel = weeklyView;
    }

    public void completeSignal() {
        CompleteActivityForm caf = new CompleteActivityForm(view.getViewModel(), this);
        view.showCompleteForm(caf);
    }

    public void confirmComplete(Duration duration) {
        view.getViewModel().getActivity().complete(duration);
        view.hideCompleteForm();
        viewModel.notifyListeners();
    }

    public void cancelComplete() {
        view.hideCompleteForm();
    }
}
