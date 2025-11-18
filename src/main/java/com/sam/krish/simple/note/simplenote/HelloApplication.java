package com.sam.krish.simple.note.simplenote;

import com.sam.krish.simple.note.simplenote.preference.UserPreference;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application {

    public static Stage currentStage;
    public static Scene currentScene;

    @Override
    public void start(Stage stage) throws IOException {

        UserPreference preference = new  UserPreference();

        if (preference.getRememberMe()){
            currentStage = stage;
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main-view.fxml"));
            currentScene = new Scene(fxmlLoader.load(), 800, 600);
            currentStage.setTitle("Simple Note");
            currentStage.setScene(currentScene);
            currentStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("notes.png"))));
            currentStage.show();
        }else  {
            currentStage = stage;
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-info.fxml"));
            currentScene = new Scene(fxmlLoader.load(), 800, 600);
            currentStage.setTitle("Simple Note");
            currentStage.setScene(currentScene);
            currentStage.show();
        }

    }
}
