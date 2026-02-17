package it.unicam.cs.mpgc.jtime119200.controllers;

import it.unicam.cs.mpgc.jtime119200.gui.ActivityView;
import it.unicam.cs.mpgc.jtime119200.gui.form.CompleteActivityForm;

import java.time.Duration;

public class CompleteActivityController {

    private final ActivityView view;

    public CompleteActivityController(ActivityView view) {
        this.view = view;
    }

    public void selectSignal() {
        view.createCheckButton();
    }

    public void completeSignal() {
        CompleteActivityForm caf = new CompleteActivityForm(view.getViewModel(), this);
        view.showCompleteForm(caf);
    }

    public void confirmComplete(Duration duration) {
        view.getViewModel().getActivity().complete(duration);
        view.hideCompleteForm();
    }

    public void cancelComplete() {
        view.hideCompleteForm();
    }
}
