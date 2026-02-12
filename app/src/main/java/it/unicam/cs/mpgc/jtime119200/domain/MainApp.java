package it.unicam.cs.mpgc.jtime119200.domain;

import it.unicam.cs.mpgc.jtime119200.application.CreateActivityController;
import it.unicam.cs.mpgc.jtime119200.application.EditActivityController;
import it.unicam.cs.mpgc.jtime119200.application.RemoveActivityController;
import it.unicam.cs.mpgc.jtime119200.gui.WeeklyView;
import it.unicam.cs.mpgc.jtime119200.model.ActivityViewModel;
import it.unicam.cs.mpgc.jtime119200.model.WeeklyViewModel;
import it.unicam.cs.mpgc.jtime119200.xml.CalendarReader;
import it.unicam.cs.mpgc.jtime119200.xml.CalendarWriter;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.time.LocalDate;
import java.util.function.Consumer;

public class MainApp extends Application {

    private static final int WIDTH = 1200;
    private static final int HEIGHT = 800;
    private final JtimeCalendar calendar = new JtimeCalendar();

    private final File xmlFile = new File(System.getProperty("user.home"), "calendar_data.xml");
    private final CalendarReader reader = new CalendarReader(xmlFile);
    private final CalendarWriter writer = new CalendarWriter(xmlFile);

    private WeeklyView weeklyView;
    private int weekOffset = 0;


    @Override
    public void start(Stage stage) {

        CalendarReader reader = new CalendarReader(xmlFile);
        reader.saveRead(calendar);

        WeeklyViewModel weeklyViewModel = new WeeklyViewModel(calendar, weekOffset);
        weeklyViewModel.addListener(() -> weeklyView.refresh(weeklyViewModel));


        Consumer<LocalDate> onDayHeaderClicked = date -> {
            CreateActivityController createActivityCtrl = new CreateActivityController(weeklyViewModel, date);
            createActivityCtrl.createActivitySignal();
        };

        Consumer<ActivityViewModel> onEdit = activityViewModel -> {
            EditActivityController editActivityCtrl = new EditActivityController(weeklyViewModel, activityViewModel);
            editActivityCtrl.editActivitySignal();
        };

        Consumer<ActivityViewModel> onRemove = activityViewModel -> {
            RemoveActivityController removeActivityCtrl = new RemoveActivityController(weeklyViewModel, activityViewModel);
            removeActivityCtrl.removeActivitySignal();
        };

        Runnable onPrevWeek = () -> weeklyViewModel.changeWeek(-1);

        Runnable onNextWeek = () -> weeklyViewModel.changeWeek(1);


        weeklyView = new WeeklyView(weeklyViewModel, onEdit, onRemove, onDayHeaderClicked, onPrevWeek, onNextWeek);

        // 6️⃣ Stage
        Scene scene = new Scene(weeklyView, WIDTH, HEIGHT);
        stage.setTitle("JTime – Gestione Attività 5.0");
        stage.setScene(scene);

        scene.getStylesheets().add(
                getClass().getResource("/css/style.css").toExternalForm()
        );


        stage.setMaximized(true);
        stage.show();

        // 7️⃣ Aggiornamento attività scadute
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