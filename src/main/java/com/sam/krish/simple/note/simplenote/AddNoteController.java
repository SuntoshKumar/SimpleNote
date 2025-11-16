package com.sam.krish.simple.note.simplenote;

import com.sam.krish.simple.note.simplenote.data.Note;
import com.sam.krish.simple.note.simplenote.data.NoteDatabase;
import com.sam.krish.simple.note.simplenote.preference.NoteColor;
import com.sam.krish.simple.note.simplenote.preference.NoteFontSize;
import com.sam.krish.simple.note.simplenote.preference.NoteFontStyle;
import com.sam.krish.simple.note.simplenote.preference.NoteFontWeight;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

public class AddNoteController {

    @FXML
    private AnchorPane root;
    @FXML
    private Button add;
    @FXML
    private TextArea title;
    @FXML
    private TextArea text;

    @FXML
    private ChoiceBox<String> fontStyle;

    @FXML
    private ChoiceBox<String> fontWeight;

    @FXML
    private ChoiceBox<String> fontSize;

    @FXML
    private ChoiceBox<String> fontColor;

    private NoteDatabase database;

    @FXML
    public void initialize() throws SQLException {

        database = new NoteDatabase();

        Note note = MainViewController.pNote;

        applyDefaultStyles(note);

        ObservableList<String> styleList =
                FXCollections.observableArrayList(
                        Arrays.stream(NoteFontStyle.values())
                                .map(Enum::name)
                                .toList()
                );
        fontStyle.setItems(styleList);
        if (note != null) {
            fontStyle.getSelectionModel().select(NoteFontStyle.fromIndex(note.getFontStyle()).name());

        } else {
            fontStyle.getSelectionModel().selectFirst();
        }

        fontStyle.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            applyStyles();
        });

        ObservableList<String> weightList =
                FXCollections.observableArrayList(
                        Arrays.stream(NoteFontWeight.values())
                                .map(Enum::name)
                                .toList()
                );
        fontWeight.setItems(weightList);
        if (note != null) {
            fontWeight.getSelectionModel().select(NoteFontWeight.fromIndex(note.getFontWeight()).name());
        } else {
            fontWeight.getSelectionModel().selectFirst();
        }

        fontWeight.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            applyStyles();
        });


        ObservableList<String> sizeList =
                FXCollections.observableArrayList(
                        Arrays.stream(NoteFontSize.values())
                                .map(Enum::name)
                                .toList()
                );
        fontSize.setItems(sizeList);

        if (note != null) {
            fontSize.getSelectionModel().select(NoteFontSize.fromIndex(note.getFontSize()).name());
        } else {
            fontSize.getSelectionModel().select(1);
        }

        fontSize.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            applyStyles();
        });


        ObservableList<String> fontColorList =
                FXCollections.observableArrayList(
                        Arrays.stream(NoteColor.values())
                                .map(Enum::name)
                                .toList()
                );
        fontColor.setItems(fontColorList);
        if (note != null) {
            fontColor.getSelectionModel().select(NoteColor.fromIndex(note.getTextColor()).name());
            BackgroundFill fill = new BackgroundFill(Color.web(NoteColor.fromIndex(note.getTextColor()).getBackgroundHex()), CornerRadii.EMPTY, new Insets(0));
            root.setBackground(new Background(fill));

        } else {
            BackgroundFill fill = new BackgroundFill(Color.web(NoteColor.DEFAULT.getBackgroundHex()), CornerRadii.EMPTY, new Insets(0));
            root.setBackground(new Background(fill));
            fontColor.getSelectionModel().selectFirst();

        }


        fontColor.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {

            BackgroundFill fill = new BackgroundFill(Color.web(NoteColor.valueOf(newValue).getBackgroundHex()), CornerRadii.EMPTY, new Insets(0));
            root.setBackground(new Background(fill));

            applyStyles();
        });

        if (note != null) {
            title.setText(note.getTitle());
            text.setText(note.getText());
        }

        title.setOnKeyPressed(keyEvent -> {
            if (Objects.requireNonNull(keyEvent.getCode()) == KeyCode.ENTER) {
                text.requestFocus();
            }
        });

    }

    public void onDone() throws IOException {
        String noteTitle = title.getText();
        String noteText = text.getText();
        int noteStyle = NoteFontStyle.getIndexByName(fontStyle.getSelectionModel().getSelectedItem());
        int noteSize = NoteFontSize.getIndexByName(fontSize.getSelectionModel().getSelectedItem());
        int noteWeight = NoteFontWeight.getIndexByName(fontWeight.getSelectionModel().getSelectedItem());
        int noteColor = NoteColor.getIndexByName(fontColor.getSelectionModel().getSelectedItem());

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss:SSS");
        String createdDate = format.format(new Date());

        if (noteTitle.isEmpty() && noteText.isEmpty()) {
            TransitionUtil.slideFade("main-view.fxml", 300, TransitionUtil.Direction.RIGHT);
            return;
        }

        Note pNote = MainViewController.pNote;

        if (pNote != null) {
            Note note = new Note(
                    pNote.getId(),
                    noteTitle,
                    noteText,
                    noteSize,
                    noteWeight,
                    noteStyle,
                    noteColor,
                    noteColor,
                    createdDate
            );
            database.update(note);
        } else {
            Note note = new Note(
                    0,
                    noteTitle,
                    noteText,
                    noteSize,
                    noteWeight,
                    noteStyle,
                    noteColor,
                    noteColor,
                    createdDate
            );
            database.insert(note);
        }

        TransitionUtil.slideFade("main-view.fxml", 300, TransitionUtil.Direction.RIGHT);
    }


    private void applyDefaultStyles(Note note) {

        String css = "";
        String css2 = "";

        if (note == null) {
            // ENUM conversions
            NoteFontStyle style = NoteFontStyle.NORMAL;

            NoteFontSize size = NoteFontSize.NORMAL;

            NoteFontWeight weight = NoteFontWeight.REGULAR;

            NoteColor txtColor = NoteColor.DEFAULT;

            NoteColor bgColor = NoteColor.DEFAULT;

            // Build CSS styles
            css = "-fx-font-style: normal;" +
                    "-fx-font-size: " + getFontSizeValue(size) + "px;" +
                    "-fx-font-weight: " + getFontWeightValue(weight) + ";" +
                    "-fx-text-fill: #" + txtColor.getTextHex() + ";" +
                    "-fx-control-inner-background: #" + bgColor.getBackgroundHex() + ";";

            css2 = "-fx-font-style: normal;" +
                    "-fx-font-size: 22px;" +
                    "-fx-font-weight: bold;" +
                    "-fx-text-fill: #" + txtColor.getTextHex() + ";" +
                    "-fx-control-inner-background: #" + bgColor.getBackgroundHex() + ";";

        } else {
            // ENUM conversions
            NoteFontStyle style = NoteFontStyle.fromIndex(note.getFontStyle());

            NoteFontSize size = NoteFontSize.fromIndex(note.getFontSize());

            NoteFontWeight weight = NoteFontWeight.fromIndex(note.getFontWeight());

            NoteColor txtColor = NoteColor.fromIndex(note.getTextColor());

            NoteColor bgColor = NoteColor.fromIndex(note.getBackgroundColor());

            // Build CSS styles
            css = "-fx-font-style: " + (style == NoteFontStyle.ITALIC ? "italic;" : "normal;") +
                    "-fx-font-size: " + getFontSizeValue(size) + "px;" +
                    "-fx-font-weight: " + getFontWeightValue(weight) + ";" +
                    "-fx-text-fill: #" + txtColor.getTextHex() + ";" +
                    "-fx-control-inner-background: #" + bgColor.getBackgroundHex() + ";";

            css2 = "-fx-font-style: normal;" +
                    "-fx-font-size: 22px;" +
                    "-fx-font-weight: bold;" +
                    "-fx-text-fill: #" + txtColor.getTextHex() + ";" +
                    "-fx-control-inner-background: #" + bgColor.getBackgroundHex() + ";";
        }


        text.setStyle(css);
        title.setStyle(css2);

    }

    private String getFontWeightValue(NoteFontWeight weight) {
        return switch (weight) {
            case BOLD -> "bold";
            case EXTRA_BOLD -> "900";
            default -> "normal";
        };
    }

    private int getFontSizeValue(NoteFontSize size) {
        return switch (size) {
            case SMALL -> 12;
            case NORMAL -> 14;
            case MEDIUM -> 18;
            case LARGE -> 22;
            case EXTRA_LARGE -> 26;
            default -> 14;
        };
    }

    private void applyStyles() {

        NoteFontStyle style = NoteFontStyle.valueOf(fontStyle.getSelectionModel().getSelectedItem());
        NoteFontSize size = NoteFontSize.valueOf(fontSize.getSelectionModel().getSelectedItem());
        NoteFontWeight weight = NoteFontWeight.valueOf(fontWeight.getSelectionModel().getSelectedItem());
        NoteColor txtColor = NoteColor.valueOf(fontColor.getSelectionModel().getSelectedItem());
        NoteColor bgColor = NoteColor.valueOf(fontColor.getSelectionModel().getSelectedItem());

        String css =
                "-fx-font-style: " + (style == NoteFontStyle.ITALIC ? "italic;" : "normal;") +
                "-fx-font-size: " + getFontSizeValue(size) + "px;" +
                "-fx-font-weight: " + getFontWeightValue(weight) + ";" +
                "-fx-text-fill: #" + txtColor.getTextHex() + ";" +
                "-fx-control-inner-background: #" + bgColor.getBackgroundHex() + ";";

        String css2 =
                "-fx-font-style: " + (style == NoteFontStyle.ITALIC ? "italic;" : "normal;") +
                        "-fx-font-size: 22px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #" + txtColor.getTextHex() + ";" +
                        "-fx-control-inner-background: #" + bgColor.getBackgroundHex() + ";";

        title.setStyle(css2);
        text.setStyle(css);
    }
}
