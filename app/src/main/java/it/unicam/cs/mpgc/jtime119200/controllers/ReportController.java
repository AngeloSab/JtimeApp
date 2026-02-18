package it.unicam.cs.mpgc.jtime119200.controllers;

import it.unicam.cs.mpgc.jtime119200.domain.Project;
import it.unicam.cs.mpgc.jtime119200.model.WeeklyViewModel;

public class ReportController {

    private final Project project;
    private final WeeklyViewModel viewModel;

    public ReportController(WeeklyViewModel weeklyViewModel, Project project) {
        this.project = project;
        this.viewModel = weeklyViewModel;
    }

    public Project getProject() {
        return project;
    }

    public void deleteProject() {
        viewModel.getCalendar().removeProject(project);
    }
}
