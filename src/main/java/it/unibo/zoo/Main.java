package it.unibo.zoo;

import it.unibo.zoo.controller.NavigationController;
import it.unibo.zoo.view.MainView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Entry point dell'applicazione Zoo Manager.
 */
public class Main extends Application {

    @Override
    public void start(final Stage primaryStage) {
        final MainView mainView = new MainView();
        final NavigationController navController = new NavigationController(mainView);

        // Mostra la HomeView come schermata iniziale
        navController.navigaHome();

        final Scene scene = new Scene(mainView.getRoot(), 1100, 700);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Zoo Manager");
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(550);
        primaryStage.show();
    }

    public static void main(final String[] args) {
        launch(args);
    }
}
