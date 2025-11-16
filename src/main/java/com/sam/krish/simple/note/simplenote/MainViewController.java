package com.sam.krish.simple.note.simplenote;

import com.sam.krish.simple.note.simplenote.data.Note;
import com.sam.krish.simple.note.simplenote.data.NoteDatabase;
import com.sam.krish.simple.note.simplenote.preference.NoteColor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Objects;

public class MainViewController {

    static Note pNote = null;

    @FXML
    private Button add;

    @FXML
    private ListView<Note> listView;


    @FXML
    public void initialize() throws SQLException {
        NoteDatabase database = new NoteDatabase();
        final ObservableList<Note> noteList = database.getAllNoteList();

        listView.setItems(noteList);

        listView.setCellFactory(lv -> {


            ListCell<Note> cell = new ListCell<Note>() {

                @Override
                protected void updateItem(Note note, boolean b) {
                    super.updateItem(note, b);

                    if (b || note == null) {
                        setGraphic(null);
                    } else {


                        VBox vBox = new VBox();
                        BackgroundFill fill = new BackgroundFill(Color.web(NoteColor.fromIndex(note.getTextColor()).getBackgroundHex()), CornerRadii.EMPTY, new Insets(0));
                        vBox.setBackground(new Background(fill));
                        vBox.setPadding(new Insets(16));
                        vBox.setStyle("-fx-background-radius: 24px; -fx-background-color: " + NoteColor.fromIndex(note.getTextColor()).getBackgroundHex() + ";");
                       listView.getStylesheets().add(Objects.requireNonNull(getClass().getResource("list-item-style.css")).toExternalForm());

                        Label title = new Label();
                        title.setTextFill(Color.GRAY);
                        title.setStyle("-fx-font-size: 18; -fx-font-weight: bold");
                        title.setText(note.getTitle());
                        title.setTextFill(Color.web(NoteColor.fromIndex(note.getTextColor()).getTextHex()));


                        Label text = new Label();
                        text.setPrefHeight(36);
                        text.setMaxHeight(36);
                        text.setTextFill(Color.GRAY);
                        text.setStyle("-fx-font-size: 14");
                        text.setText(note.getText());
                        text.setTextFill(Color.web(NoteColor.fromIndex(note.getTextColor()).getTextHex()));

                        Label date = new Label();
                        date.setTextFill(Color.GRAY);
                        date.setStyle("-fx-font-size: 12");
                        date.setText(note.getCreatedDate());

                        vBox.getChildren().addAll(title, text, date);
                        setGraphic(vBox);

                        vBox.setOnMouseClicked(mouseEvent -> {
                            pNote = note;
                            try {

                                TransitionUtil.slideFade("add-note.fxml", 300, TransitionUtil.Direction.LEFT);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });

                    }
                }
            };

            ContextMenu contextMenu = new ContextMenu();
            contextMenu.setStyle("-fx-background-color: #ffffff;\n" +
                    "    -fx-background-radius: 12;\n" +
                    "    -fx-padding: 8;\n" +
                    "    -fx-border-color: #dddddd;\n" +
                    "    -fx-border-radius: 12;\n" +
                    "    -fx-border-width: 1;\n" +
                    "    -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.18), 10, 0.2, 0, 2);");
            MenuItem open = new MenuItem(" Open ");
            MenuItem copy = new MenuItem(" Copy ");
            MenuItem delete = new MenuItem(" Delete ");




            contextMenu.getItems().addAll(open, copy, delete);

            cell.setOnContextMenuRequested(e -> {
                if (!cell.isEmpty()) {
                    contextMenu.show(cell, e.getScreenX(), e.getScreenY());
                }
            });

            delete.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    database.delete(cell.getItem());
                    noteList.clear();
                    noteList.addAll(database.getAllNoteList());
                }
            });

            copy.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    Clipboard clipboard = Clipboard.getSystemClipboard();
                    ClipboardContent content = new ClipboardContent();

                    content.putString(cell.getItem().getTitle() + "\n\n" + cell.getItem().getText()); // or getTitle(), or both
                    clipboard.setContent(content);
                }
            });

            open.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    pNote = cell.getItem();
                    try {

                        TransitionUtil.slideFade("add-note.fxml", 300, TransitionUtil.Direction.LEFT);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

            return cell;
        });





    }

    public void onAddNote() throws IOException {
        pNote = null;
        TransitionUtil.slideFade("add-note.fxml", 300, TransitionUtil.Direction.LEFT);
    }



}
