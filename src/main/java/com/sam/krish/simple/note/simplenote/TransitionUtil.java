package com.sam.krish.simple.note.simplenote;

import javafx.animation.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Objects;

public class TransitionUtil {

    // --------------------------
    // Load FXML
    // --------------------------
    public static Parent load(String fxmlPath) throws IOException {
        return FXMLLoader.load(Objects.requireNonNull(TransitionUtil.class.getResource(fxmlPath)));
    }


    // --------------------------
    // Fade Transition (on Scene Root)
    // --------------------------
    public static void fadeTo(Stage stage, String fxmlPath, int duration) throws IOException {
        Parent newRoot = load(fxmlPath);
        Scene scene = stage.getScene();

        newRoot.setOpacity(0);
        scene.setRoot(newRoot);

        FadeTransition ft = new FadeTransition(Duration.millis(duration), newRoot);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();
    }


    // --------------------------
    // Fade inside StackPane
    // --------------------------
    public static void fadeIn(StackPane container, String fxmlPath, int duration) throws IOException {
        Parent newPage = load(fxmlPath);
        newPage.setOpacity(0);

        container.getChildren().setAll(newPage);

        FadeTransition ft = new FadeTransition(Duration.millis(duration), newPage);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();
    }


    // --------------------------
    // Slide Transition inside StackPane
    // --------------------------
    public static void slide(StackPane container, String fxmlPath, int duration, Direction direction) throws IOException {
        Parent newPage = load(fxmlPath);
        Parent oldPage = container.getChildren().isEmpty() ? null : container.getChildren().getFirst().getParent();

        double width = container.getWidth();
        double height = container.getHeight();

        // Set start position
        switch (direction) {
            case LEFT -> newPage.setTranslateX(width);
            case RIGHT -> newPage.setTranslateX(-width);
            case UP -> newPage.setTranslateY(height);
            case DOWN -> newPage.setTranslateY(-height);
        }

        container.getChildren().setAll(newPage);

        TranslateTransition tt = new TranslateTransition(Duration.millis(duration), newPage);

        tt.setToX(0);
        tt.setToY(0);

        tt.play();
    }


    // --------------------------
    // Slide + Fade (smoothest)
    // --------------------------

    /***
     * Swap scene in stage with slide and fade transition
     * @param resourceName ui resource file name with extension
     * @param duration duration in millisecond for transition
     * @param direction slide direction left,right,top, and bottom
     * @throws IOException handle exception for loading ui file
     */
    public static void slideFade(String resourceName, int duration, Direction direction) throws IOException {
        FXMLLoader loader = new FXMLLoader(TransitionUtil.class.getResource(resourceName));
        Parent newRoot = loader.load();

        TranslateTransition slide = new TranslateTransition(Duration.millis(duration), newRoot);
        switch (direction) {
            case LEFT -> {
                slide.setFromX(300);
                slide.setToX(0);
            }
            case RIGHT -> {
                slide.setFromX(-300);
                slide.setToX(0);
            }
            case UP -> {
                slide.setFromY(300);
                slide.setToY(0);
            }
            case DOWN -> {
                slide.setFromY(-300);
                slide.setToY(0);
            }
        }
//        slide.setFromX(300);
//        slide.setToX(0);

        FadeTransition fade = new FadeTransition(Duration.millis(duration), newRoot);
        fade.setFromValue(0);
        fade.setToValue(1);

        ParallelTransition pt = new ParallelTransition(slide, fade);

        HelloApplication.currentStage.getScene().setRoot(newRoot);

        pt.play();
    }


    // --------------------------
    // Direction Enum
    // --------------------------
    public enum Direction {
        LEFT, RIGHT, UP, DOWN
    }
}
