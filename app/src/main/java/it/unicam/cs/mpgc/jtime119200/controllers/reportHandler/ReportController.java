package it.unicam.cs.mpgc.jtime119200.controllers.reportHandler;

import it.unicam.cs.mpgc.jtime119200.domain.Project;
import it.unicam.cs.mpgc.jtime119200.gui.ProjectReportView;
import it.unicam.cs.mpgc.jtime119200.model.WeeklyViewModel;

/**
 * Controller responsible for handling project reports.
 * This class acts as a Report handler and is responsible for:
 *  - Managing user interactions related to a project's report
 *  - Providing access to the associated {@link Project}
 *  - Deleting the project from the calendar domain model
 *  - Showing the report UI via {@link ProjectReportView}
 */
public class ReportController implements ReportInterface {

    private final Project project;
    private final WeeklyViewModel viewModel;

    /**
     * Constructs a new ReportController for a specific project.
     *
     * @param weeklyViewModel the {@link WeeklyViewModel} of the current week
     * @param project the {@link Project} associated with this report
     */
    public ReportController(WeeklyViewModel weeklyViewModel, Project project) {
        this.project = project;
        this.viewModel = weeklyViewModel;
    }

    /**
     * Returns the project associated with this controller.
     *
     * @return the {@link Project} instance
     */
    public Project getProject() {
        return project;
    }

    /**
     * Deletes the associated project from the calendar.
     */
    public void deleteProject() {
        viewModel.getCalendar().removeProject(project);
    }

    /**
     * Shows the report view for the associated project.
     * This method launches a {@link ProjectReportView} where the user
     * can see project details and statistics.
     */
    @Override
    public void showReportSignal(){
        new ProjectReportView(this);
    }
}