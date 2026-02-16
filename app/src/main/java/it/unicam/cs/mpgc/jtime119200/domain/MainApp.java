package it.unicam.cs.mpgc.jtime119200.domain;

import it.unicam.cs.mpgc.jtime119200.application.CompleteActivityController;
import it.unicam.cs.mpgc.jtime119200.application.CreateActivityController;
import it.unicam.cs.mpgc.jtime119200.application.EditActivityController;
import it.unicam.cs.mpgc.jtime119200.application.RemoveActivityController;
import it.unicam.cs.mpgc.jtime119200.gui.ActivityView;
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
    private final CalendarWriter writer = new CalendarWriter(xmlFile);

    private WeeklyView weeklyView;


    @Override
    public void start(Stage stage) {

        CalendarReader reader = new CalendarReader(xmlFile);
        reader.saveRead(calendar);

        int weekOffset = 0;
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

        Consumer<ActivityView> onSelect = activityView -> {
            CompleteActivityController completeActivityCtrl = new CompleteActivityController(activityView);
            completeActivityCtrl.selectSignal();
        };

        Consumer<ActivityView> onComplete = activityView -> {
            CompleteActivityController completeActivityCtrl = new CompleteActivityController(activityView);
            completeActivityCtrl.completeSignal();
        };

        Runnable onPrevWeek = () -> weeklyViewModel.changeWeek(-1);

        Runnable onNextWeek = () -> weeklyViewModel.changeWeek(1);


        weeklyView = new WeeklyView(weeklyViewModel, onEdit, onRemove, onSelect, onComplete,
                onDayHeaderClicked, onPrevWeek, onNextWeek);

        // 6️⃣ Stage
        Scene scene = new Scene(weeklyView, WIDTH, HEIGHT);
        stage.setTitle("Jtime Calendar - Manage your Activities and Projects");
        stage.setScene(scene);

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