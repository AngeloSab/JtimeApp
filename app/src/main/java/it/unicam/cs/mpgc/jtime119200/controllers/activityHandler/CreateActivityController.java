package it.unicam.cs.mpgc.jtime119200.controllers.activityHandler;

import it.unicam.cs.mpgc.jtime119200.domain.JtimeCalendar;
import it.unicam.cs.mpgc.jtime119200.gui.form.CreateAndEditActivityForm;
import it.unicam.cs.mpgc.jtime119200.model.form.CreateAndEditActivityFormModel;
import it.unicam.cs.mpgc.jtime119200.model.WeeklyViewModel;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;

/**
 * Controller responsible for creating new activities within the calendar.
 * This class acts as an ActivityHandler and is responsible for:
 *  - Managing user interactions related to creating a new activity
 *  - Communicating with the {@link JtimeCalendar} domain object to persist new activities
 *  - Updating the {@link WeeklyViewModel} to refresh the UI
 *  - Opening the activity creation form
 */
public class CreateActivityController implements HandleActivityInterface {

    private final WeeklyViewModel weeklyViewModel;
    private final LocalDate day;
    private final JtimeCalendar calendar;

    /**
     * Constructs a new CreateActivityController for a specific day.
     *
     * @param weeklyViewModel the view model of the current week
     * @param day the date for which a new activity will be created
     */
    public CreateActivityController(WeeklyViewModel weeklyViewModel, LocalDate day) {
        this.weeklyViewModel = weeklyViewModel;
        this.day = day;
        this.calendar = weeklyViewModel.getCalendar();
    }

    /**
     * Creates a new activity in the calendar.
     *
     * @param projectName the name of the project this activity belongs to
     * @param title the title of the activity
     * @param expectedDuration the planned duration of the activity
     * @param startTime the start time of the activity
     * @param day the date of the activity
     */
    public void createActivity(String projectName, String title, Duration expectedDuration, Instant startTime, LocalDate day) {
        calendar.createActivity(calendar.createProject(projectName), title, expectedDuration, startTime, day);
        weeklyViewModel.notifyListeners();
    }

    /**
     * Opens the form for creating a new activity.
     * This method constructs a {@link CreateAndEditActivityFormModel} and
     * launches a {@link CreateAndEditActivityForm} for the specified day.
     */
    @Override
    public void showActivityHandlerSignal() {
        CreateAndEditActivityFormModel afm = new CreateAndEditActivityFormModel(weeklyViewModel, day);
        new CreateAndEditActivityForm(afm, day);
    }
}