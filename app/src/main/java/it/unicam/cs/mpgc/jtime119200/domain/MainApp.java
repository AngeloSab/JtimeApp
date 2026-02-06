package it.unicam.cs.mpgc.jtime119200.domain;

import it.unicam.cs.mpgc.jtime119200.gui.DayColumnView;
import it.unicam.cs.mpgc.jtime119200.gui.NewActivityView;
import it.unicam.cs.mpgc.jtime119200.gui.WeeklyView;
import it.unicam.cs.mpgc.jtime119200.model.ActivityViewModel;
import it.unicam.cs.mpgc.jtime119200.model.WeeklyViewModel;
import it.unicam.cs.mpgc.jtime119200.xml.CalendarReader;
import it.unicam.cs.mpgc.jtime119200.xml.CalendarWriter;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.time.LocalDate;
import java.util.function.Consumer;

public class MainApp extends Application {

    private static final int WIDTH = 1100;
    private static final int HEIGHT = 700;

    private JtimeCalendar calendar;
    private WeeklyView weeklyView;
    private int weekOffset = 0;

    @Override
    public void start(Stage stage) {

        // 1️⃣ Dominio + XML
        calendar = new JtimeCalendar();
        File xmlFile = new File(System.getProperty("user.home"), "calendar_data.xml");

        CalendarReader reader = new CalendarReader(xmlFile);
        CalendarWriter writer = new CalendarWriter(xmlFile);
        reader.saveRead(calendar);

        // 2️⃣ ViewModel iniziale
        WeeklyViewModel viewModel = new WeeklyViewModel(calendar, weekOffset);

        // 3️⃣ Callback selezione attività
        Consumer<ActivityViewModel> onActivitySelected = activityVM -> {
            System.out.println("Activity selected: " + activityVM.getTitle());
            // qui in futuro ActivityReportView
        };

        Consumer<LocalDate> onDayHeaderClicked = date -> {
            NewActivityView view = new NewActivityView(date);

            Stage newActivityStage = new Stage();
            stage.setTitle("New Activity");
            stage.setScene(new Scene(view));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            if (view.isConfirmed()) {
                activityController.createActivity(
                        view.getProject(),
                        view.getTitle(),
                        view.getExpectedDuration(),
                        view.getStartTime(),
                        date
                );
                refreshWeek();
            }
        };

        // 4️⃣ Callback navigazione
        Runnable onPrevWeek = () -> changeWeek(-1);
        Runnable onNextWeek = () -> changeWeek(1);

        // 5️⃣ View
        weeklyView = new WeeklyView(viewModel, onActivitySelected, onPrevWeek, onNextWeek);

        // 6️⃣ Stage
        Scene scene = new Scene(weeklyView, WIDTH, HEIGHT);
        stage.setTitle("JTime – Gestione Attività 5.0");
        stage.setScene(scene);
        stage.show();

        // 7️⃣ Aggiornamento attività scadute
        calendar.updateExpiredAcitvities();
    }

    private void changeWeek(int offset) {
        weekOffset += offset;
        WeeklyViewModel newModel = new WeeklyViewModel(calendar, weekOffset);
        weeklyView.setViewModel(newModel);
    }

    @Override
    public void stop() {
        File xmlFile = new File(System.getProperty("user.home"), "calendar_data.xml");
        CalendarWriter writer = new CalendarWriter(xmlFile);
        writer.saveWrite(calendar);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
