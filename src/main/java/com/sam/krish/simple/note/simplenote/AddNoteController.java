package com.sam.krish.simple.note.simplenote;

import com.sam.krish.simple.note.simplenote.data.Note;
import com.sam.krish.simple.note.simplenote.data.NoteDatabase;
import com.sam.krish.simple.note.simplenote.preference.NoteColor;
import com.sam.krish.simple.note.simplenote.preference.NoteFontSize;
import com.sam.krish.simple.note.simplenote.preference.NoteFontStyle;
import com.sam.krish.simple.note.simplenote.preference.NoteFontWeight;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

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
    private HBox topItem;
    @FXML
    private Button add;
    @FXML
    private TextField title;
    @FXML
    private TextArea text;


    @FXML
    private ChoiceBox<String> fontWeight;

    @FXML
    private ChoiceBox<String> fontSize;


    private NoteDatabase database;

    private int cIndex;

    private Circle selectedCircle = null; // track currently selected circle

    Note pNote = MainViewController.pNote;

    @FXML
    public void initialize() throws SQLException {

        Note note = MainViewController.pNote;
        String defaultColor = note != null ? NoteColor.fromIndex(note.getTextColor()).name() : NoteColor.DEFAULT.name();
        topItem.getChildren().add(colorSelector(defaultColor));

        database = new NoteDatabase();

        applyDefaultStyles(note);

        if (pNote != null) {
            cIndex = pNote.getTextColor();
        }

        ObservableList<String> weightList =
                FXCollections.observableArrayList(
                        Arrays.stream(NoteFontWeight.values())
                                .map(Enum::name)
                                .toList()
                );

        fontWeight.getStylesheets().add(
                Objects.requireNonNull(getClass().getResource("choice-box.css")).toExternalForm()
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


        fontSize.getStylesheets().add(
                Objects.requireNonNull(getClass().getResource("choice-box.css")).toExternalForm()
        );
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

        if (note != null) {
            title.setText(note.getTitle());
            text.setText(note.getText());
        }

        title.setOnKeyPressed(keyEvent -> {
            if (Objects.requireNonNull(keyEvent.getCode()) == KeyCode.ENTER) {
                text.requestFocus();
            }
        });


        Platform.runLater(() -> {
            title.requestFocus();
            title.positionCaret(title.getText().length());
        });
    }

    public void onDone() throws IOException {
        String noteTitle = title.getText();
        String noteText = text.getText();
        int noteSize = NoteFontSize.getIndexByName(fontSize.getSelectionModel().getSelectedItem());
        int noteWeight = NoteFontWeight.getIndexByName(fontWeight.getSelectionModel().getSelectedItem());
        int noteColor = cIndex;

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss:SSS");
        String createdDate = format.format(new Date());

        if (noteTitle.isEmpty() && noteText.isEmpty()) {
            TransitionUtil.slideFade("main-view.fxml", 300, TransitionUtil.Direction.RIGHT);
            return;
        }

        Note pNote = MainViewController.pNote;

        System.out.println("PNoteeee " + pNote);

        if (pNote != null) {
            Note note = new Note(
                    pNote.getId(),
                    noteTitle,
                    noteText,
                    noteSize,
                    noteWeight,
                    0,
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
                    0,
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

        String sizeName = fontSize.getSelectionModel().getSelectedItem();
        NoteFontSize size = sizeName != null ? NoteFontSize.valueOf(sizeName) : NoteFontSize.NORMAL;

        String weightName = fontWeight.getSelectionModel().getSelectedItem();
        NoteFontWeight weight = weightName != null ? NoteFontWeight.valueOf(weightName) : NoteFontWeight.REGULAR;

        NoteColor txtColor = NoteColor.fromIndex(cIndex);
        NoteColor bgColor = NoteColor.fromIndex(cIndex);

        String css =
                "-fx-font-size: " + getFontSizeValue(size) + "px;" +
                        "-fx-font-weight: " + getFontWeightValue(weight) + ";" +
                        "-fx-text-fill: #" + txtColor.getTextHex() + ";" +
                        "-fx-control-inner-background: #" + bgColor.getBackgroundHex() + ";";

        String css2 =
                        "-fx-font-size: 22px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #" + txtColor.getTextHex() + ";" +
                        "-fx-control-inner-background: #" + bgColor.getBackgroundHex() + ";";

        title.setStyle(css2);
        text.setStyle(css);
    }


    public Node colorSelector(String defaultColorName) {
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(8));
        hBox.setSpacing(8);

        for (NoteColor color : NoteColor.values()) {
            Circle circle = new Circle(12, Color.web(color.getTextHex()));
            HBox.setMargin(circle, new Insets(4));

            circle.setOnMouseClicked(event -> {
                cIndex = color.getIndex();
                selectCircle(circle, color);
            });

            hBox.getChildren().add(circle);

            // If this circle matches the default color, select it
            if (color.name().equalsIgnoreCase(defaultColorName)) {
                selectCircle(circle, color);
            }
        }

        return hBox;
    }

    private void selectCircle(Circle circle, NoteColor color) {
        // Remove highlight from previous circle
        if (selectedCircle != null) {
            selectedCircle.setStroke(null);
            selectedCircle.setStrokeWidth(0);
        }

        // Highlight current circle
        circle.setStroke(Color.BLACK);
        circle.setStrokeWidth(2);
        selectedCircle = circle;

        // Update ChoiceBox and TextArea background
        BackgroundFill fill = new BackgroundFill(
                Color.web(color.getBackgroundHex()), CornerRadii.EMPTY, Insets.EMPTY
        );
        root.setBackground(new Background(fill));
        applyStyles();
    }
}
