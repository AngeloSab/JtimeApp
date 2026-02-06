package it.unicam.cs.mpgc.jtime119200.model;

import it.unicam.cs.mpgc.jtime119200.domain.Activity;
import it.unicam.cs.mpgc.jtime119200.domain.JtimeCalendar;
import it.unicam.cs.mpgc.jtime119200.domain.Project;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * ViewModel per gestire il form di creazione o modifica di una Activity.
 * Non contiene logica di GUI, solo dati e validazioni.
 */
public class ActivityFormViewModel {

    private final JtimeCalendar calendar;

    public ActivityFormViewModel(JtimeCalendar calendar) {
        this.calendar = Objects.requireNonNull(calendar);
    }

    public List<Project> getAllProjects() {
        return calendar.getProjects();
    }

    public Activity buildActivity(Project project,
                                  String title,
                                  Duration expectedDuration,
                                  Instant startTime,
                                  LocalDate date) {
        Objects.requireNonNull(project, "Project cannot be null");
        Objects.requireNonNull(title, "Title cannot be null");
        Objects.requireNonNull(expectedDuration, "Expected duration cannot be null");
        Objects.requireNonNull(startTime, "Start time cannot be null");
        Objects.requireNonNull(date, "Date cannot be null");

        return calendar.createActivity(project, title, expectedDuration, startTime, date);
    }

}

