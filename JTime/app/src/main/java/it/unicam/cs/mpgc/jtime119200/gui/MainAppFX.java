package it.unicam.cs.mpgc.jtime119200.gui;

import it.unicam.cs.mpgc.jtime119200.xml.CalendarReader;
import it.unicam.cs.mpgc.jtime119200.xml.CalendarWriter;
import it.unicam.cs.mpgc.jtime119200.JtimeCalendar;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.io.File;
import java.time.LocalDate;

public class MainAppFX {
    private final JtimeCalendar calendar = new JtimeCalendar();
    private final BorderPane root;
    private final ActivityUIController controller;
    private final CompleteUIController completeController;
    File xmlFile = new File(
            System.getProperty("user.home"),
            "calendar_data.xml"
    );

    private final CalendarWriter writer = new CalendarWriter(xmlFile);


    public MainAppFX() {
        this.root = new BorderPane();

        this.controller = new ActivityUIController(calendar);
        this.completeController = new CompleteUIController(calendar);
        completeController.setOnActivityCompleted(activity -> showWeeklyView());

        controller.setOnCalendarChanged(this::showWeeklyView);
        completeController.setOnCalendarChanged(this::showWeeklyView);

        CalendarReader reader = new CalendarReader(xmlFile);
        reader.saveRead(calendar);
        showWeeklyView();
    }


    public void showWeeklyView() {
        WeeklyView weeklyView = new WeeklyView(controller, this::showDailyViewForDate);

        completeController.setOnActivityCompleted(weeklyView::showReport);

        writer.saveWrite(calendar);

        root.setCenter(weeklyView);
    }


    public void showDailyViewForDate(LocalDate date) {
        DailyView dailyView = new DailyView(controller, completeController, date, this::showWeeklyView);
        root.setCenter(dailyView);
    }

    public Parent getRoot() {
        return root;
    }
}
