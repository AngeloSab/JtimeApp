package it.unicam.cs.mpgc.jtime119200.application;

import it.unicam.cs.mpgc.jtime119200.controllers.*;
import it.unicam.cs.mpgc.jtime119200.domain.JtimeCalendar;
import it.unicam.cs.mpgc.jtime119200.gui.WeeklyView;
import it.unicam.cs.mpgc.jtime119200.model.WeeklyViewModel;
import it.unicam.cs.mpgc.jtime119200.xml.CalendarReader;
import it.unicam.cs.mpgc.jtime119200.xml.CalendarWriter;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.File;

public class MainApp extends Application {

    private static final int WIDTH = 1200;
    private static final int HEIGHT = 800;
    private final JtimeCalendar calendar = new JtimeCalendar();

    private final File xmlFile = new File(System.getProperty("user.home"), "calendar_data.xml");
    private final CalendarWriter writer = new CalendarWriter(xmlFile);

    private WeeklyView weeklyView;


    @Override
    public void start(Stage stage) {

        CalendarReader reader = new CalendarReader(xmlFile);
        reader.saveRead(calendar);

        int weekOffset = 0;
        WeeklyViewModel weeklyViewModel = new WeeklyViewModel(calendar, weekOffset);
        weeklyViewModel.addListener(() -> weeklyView.refresh(weeklyViewModel));
        EventController controller = new EventController(weeklyViewModel);
        weeklyView = new WeeklyView(weeklyViewModel, controller);

        Scene scene = new Scene(weeklyView, WIDTH, HEIGHT);
        stage.setTitle("Jtime Calendar - Manage your Activities and Projects");
        stage.setScene(scene);

        scene.setOnMouseClicked(event -> {
            controller.clearSelection();
        });

        scene.getStylesheets().add(
                getClass().getResource("/css/style.css").toExternalForm()
        );


        stage.setMaximized(true);
        stage.show();

        calendar.updateExpiredActivities();
    }

    @Override
    public void stop() {
        writer.saveWrite(calendar);
    }

    public static void main(String[] args) {
        launch(args);
    }
}