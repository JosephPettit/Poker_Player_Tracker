package com.poker_player_tracker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("window_controllers/MainView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 750, 400);
        stage.setTitle("Player Tracker");
        stage.setScene(scene);
        stage.show();
    }
}