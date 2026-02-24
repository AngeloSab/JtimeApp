package it.unicam.cs.mpgc.jtime119200.application;

import it.unicam.cs.mpgc.jtime119200.controllers.*;
import it.unicam.cs.mpgc.jtime119200.domain.JtimeCalendar;
import it.unicam.cs.mpgc.jtime119200.gui.WeeklyView;
import it.unicam.cs.mpgc.jtime119200.model.WeeklyViewModel;
import it.unicam.cs.mpgc.jtime119200.repository.xml.CalendarRepositoryXML;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Entry point of the Jtime Calendar application.
 * This class is responsible for bootstrapping the entire application,
 * initializing the main components and wiring together the different layers
 * of the system following an MVC/MVVM-inspired architecture.
 * Responsibilities:
 *   Loading persisted calendar data from the repository
 *   Creating and connecting the Domain, ViewModel, Controller and View layers
 *   Initializing and displaying the primary JavaFX stage
 *   Persisting data when the application stops
 */
public class MainApp extends Application {

    private static final int WIDTH = 1200;
    private static final int HEIGHT = 800;

    private final JtimeCalendar calendar = new JtimeCalendar();
    CalendarRepositoryXML calendarRepository = new CalendarRepositoryXML();
    private WeeklyView weeklyView;

    /**
     * Initializes and starts the JavaFX application.
     *This method performs the following steps:
     *  Loads persisted calendar data from the XML repository
     *  Creates the {@link WeeklyViewModel}
     *  Registers listeners to keep the UI synchronized with the model
     *  Creates the {@link EventController}
     *  Initializes the main {@link WeeklyView}
     *  Configures the JavaFX scene and stage
     *  Applies CSS styling
     *  Updates expired activities after startup
     *
     *
     * @param stage the primary stage provided by the JavaFX runtime
     */
    @Override
    public void start(Stage stage) {
        calendarRepository.load(calendar);

        int weekOffset = 0;
        WeeklyViewModel weeklyViewModel = new WeeklyViewModel(calendar, weekOffset);
        weeklyViewModel.addListener(() -> weeklyView.refresh(weeklyViewModel));
        EventController controller = new EventController(weeklyViewModel);
        weeklyView = new WeeklyView(weeklyViewModel, controller);

        Scene scene = new Scene(weeklyView, WIDTH, HEIGHT);
        stage.setTitle("Jtime Calendar - Manage your Activities and Projects");
        stage.setScene(scene);

        scene.setOnMouseClicked(event -> controller.clearSelection());

        scene.getStylesheets().add(
                getClass().getResource("/css/style.css").toExternalForm()
        );

        stage.setMaximized(true);
        stage.show();

        calendar.updateExpiredActivities();
    }

    /**
     * Called automatically when the application is shutting down.
     *
     * <p>Ensures that the current state of the calendar is persisted
     * using the XML repository before exiting.</p>
     */
    @Override
    public void stop() {
        calendarRepository.save(calendar);
    }

    /**
     * Standard JavaFX launcher method.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}