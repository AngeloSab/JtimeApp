package it.unicam.cs.mpgc.jtime119200;

import it.unicam.cs.mpgc.jtime119200.gui.MainAppFX;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    public final int WIDTH = 1100;
    public final int HEIGHT = 700;
    @Override
    public void start(Stage primaryStage) {
        MainAppFX mainGUI = new MainAppFX(); // container GUI

        // Creiamo la Scene con la radice di MainAppFX
        Scene scene = new Scene(mainGUI.getRoot(), WIDTH, HEIGHT);

        primaryStage.setTitle("JTime – Task & Project Manager");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

