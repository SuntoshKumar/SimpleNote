package com.sam.krish.simple.note.simplenote;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    public static Stage currentStage;
    public static Scene currentScene;

    @Override
    public void start(Stage stage) throws IOException {
        currentStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        currentScene = new Scene(fxmlLoader.load(), 1600, 800);
        currentStage.setTitle("Hello!");
        currentStage.setScene(currentScene);
        currentStage.show();
    }
}
