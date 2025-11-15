package com.sam.krish.simple.note.simplenote;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class HelloController {
    public Label welcomeText;
    @FXML
    private Button hello;

    @FXML Button helloWorld;

    @FXML
    protected void onHelloButtonClick() throws IOException {
        TransitionUtil.slideFade("login-info.fxml", 200, TransitionUtil.Direction.LEFT);
    }


}
