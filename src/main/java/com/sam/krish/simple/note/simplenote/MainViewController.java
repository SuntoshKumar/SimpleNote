package com.sam.krish.simple.note.simplenote;

import com.sam.krish.simple.note.simplenote.data.Note;
import com.sam.krish.simple.note.simplenote.data.NoteDatabase;
import com.sam.krish.simple.note.simplenote.preference.NoteColor;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.SQLException;

public class MainViewController {

    static Note pNote = null;

    @FXML
    private Button add;

    @FXML
    private ListView<Note> listView;


    @FXML
    public void initialize() throws SQLException {
        NoteDatabase database = new NoteDatabase();
        listView.setItems(database.getAllNoteList());

        listView.setCellFactory(call -> new ListCell<Note>(){

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
        });
    }

    public void onAddNote() throws IOException {
        pNote = null;
        TransitionUtil.slideFade("add-note.fxml",300, TransitionUtil.Direction.LEFT);
    }


}
