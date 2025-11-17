package com.sam.krish.simple.note.simplenote;

import com.sam.krish.simple.note.simplenote.data.Note;
import com.sam.krish.simple.note.simplenote.data.NoteDatabase;
import com.sam.krish.simple.note.simplenote.helper.AlertDialogHelper;
import com.sam.krish.simple.note.simplenote.helper.BackupManager;
import com.sam.krish.simple.note.simplenote.preference.NoteColor;
import com.sam.krish.simple.note.simplenote.preference.UserPreference;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class MainViewController {

    static Note pNote = null;

    @FXML
    private Button add;

    @FXML
    private ListView<Note> listView;

    UserPreference preference;

    NoteDatabase database;
    ObservableList<Note> noteList;

    BackupManager backupManager;


    @FXML
    public void initialize() throws SQLException {
        database = new NoteDatabase();
        noteList = database.getAllNoteList();

        preference = new UserPreference();

        listView.setItems(noteList);

        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        try {
            backupManager = new BackupManager();
        } catch (Exception e) {
            e.printStackTrace();
        }

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
                        title.setStyle("-fx-font-size: 18; -fx-font-weight: bold; -fx-text-fill: #"+NoteColor.fromIndex(note.getTextColor()).getTextHex()+";");
                        title.setText(note.getTitle());
                        title.setTextFill(Color.web(NoteColor.fromIndex(note.getTextColor()).getTextHex()));


                        Label text = new Label();
                        text.setPrefHeight(36);
                        text.setMaxHeight(36);
                        text.setStyle("-fx-font-size: 14; -fx-text-fill: #"+NoteColor.fromIndex(note.getTextColor()).getTextHex()+";");
                        text.setText(note.getText());
                        text.setTextFill(Color.web(NoteColor.fromIndex(note.getTextColor()).getTextHex()));

                        Label date = new Label();
                        date.setStyle("-fx-font-size: 12; -fx-text-fill: #B0BEC5;");
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


                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.getButtonTypes().clear();
                    alert.setTitle("Delete");
                    alert.setContentText("Item will be deleted permanently!,\n\n Are you sure you want to delete ?");
                    ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                    ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
                    alert.getButtonTypes().addAll(cancelButton, okButton);
                    alert.show();

                    alert.setResultConverter(btn -> {
                        if (btn == okButton){
                            database.delete(cell.getItem());
                            noteList.clear();
                            noteList.addAll(database.getAllNoteList());
                        }
                        return null;
                    });
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

    @FXML
    public void onDelete(){
        Note note = listView.getSelectionModel().getSelectedItem();

        if (note == null) {
            // Optional: show a small alert
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Please select a note to delete.");
            alert.showAndWait();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.getButtonTypes().clear();
        alert.setTitle("Delete");
        alert.setContentText("Item will be deleted permanently!,\n\n Are you sure you want to delete ?");
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().addAll(cancelButton, okButton);
        alert.show();

        alert.setResultConverter(btn -> {
            if (btn == okButton){
                database.delete(note);
                noteList.clear();
                noteList.addAll(database.getAllNoteList());
            }
            return null;
        });


    }

    @FXML
    public void onDeleteAll(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.getButtonTypes().clear();
        alert.setTitle("Delete");
        alert.setContentText("All item will be deleted permanently!,\n\n Are you sure you want to delete everything ?");
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().addAll(cancelButton, okButton);
        alert.show();

        alert.setResultConverter(btn -> {
            if (btn == okButton){
                database.deleteAll();
                noteList.clear();
                noteList.addAll(database.getAllNoteList());
            }
            return null;
        });

    }

    @FXML
    public void onSelectAll(){
        listView.getSelectionModel().selectAll();
    }

    @FXML
    public void onDeselect(){
        listView.getSelectionModel().clearSelection();
    }
    @FXML
    public void onDeleteSelected(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.getButtonTypes().clear();
        alert.setTitle("Delete");
        alert.setContentText("Selected items will be deleted permanently!,\n\n Are you sure you want to delete selections ?");
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().addAll(cancelButton, okButton);
        alert.show();



        alert.setResultConverter(btn -> {
            if (btn == okButton){
                ObservableList<Note> selectedItems = listView.getSelectionModel().getSelectedItems();
                if (selectedItems != null){
                    for (Note note: selectedItems){
                        database.delete(note);
                    }
                    noteList.clear();
                    noteList.addAll(database.getAllNoteList());
                }
            }
            return null;
        });
    }

    @FXML
    public void onCopy(){
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();

        Note note = listView.getSelectionModel().getSelectedItem();


        if (note == null) {
            // Optional: show a small alert
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Please select a note to copy.");
            alert.showAndWait();
            return;
        }

        content.putString(note.getTitle() + "\n\n" + note.getText()); // or getTitle(), or both
        clipboard.setContent(content);
    }

    @FXML
    public void onOpen(){
        pNote = listView.getSelectionModel().getSelectedItem();
        try {

            if (pNote == null) {
                // Optional: show a small alert
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Please select a note to open.");
                alert.showAndWait();
                return;
            }
            TransitionUtil.slideFade("add-note.fxml", 300, TransitionUtil.Direction.LEFT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void onLogout() throws IOException {
        TransitionUtil.slideFade("login-info.fxml", 300, TransitionUtil.Direction.RIGHT);
        preference.setRememberMe(false);
    }

    @FXML
    public void onAbout(){

        Dialog<String> alert = new Dialog<>();

        VBox vBox = new VBox();
        vBox.setPadding(new Insets(20));
        vBox.setAlignment(Pos.CENTER);

        Label label = new Label("Simple Note");
        label.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");
        label.setPadding(new Insets(30));

        Label content = new Label("A simple note taking app for desktop environment!");

        Label version = new Label("Version 1.0.0");
        version.setPadding(new Insets(20));


        Label dev = new Label("Developed by: Than Htay And Sam Krish");
        dev.setStyle("-fx-font-size: 12; -fx-text-fill: grey;");

        vBox.getChildren().addAll(label, content, version, dev);

        alert.getDialogPane().setContent(vBox);

        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        alert.getDialogPane().getButtonTypes().add(okButton);


        alert.setResultConverter(btn -> {
            if (btn == okButton) return "OK clicked";
            return null;
        });

        alert.showAndWait();
    }

    @FXML
    public void onClose(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.getButtonTypes().clear();
        alert.setTitle("Close");
        alert.setContentText("Are you sure you want to exit ?");
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        ButtonType okButton = new ButtonType("Exit", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().addAll(cancelButton, okButton);
        alert.show();

        alert.setResultConverter(btn -> {
            if (btn == okButton){
                System.exit(0);
            }
            return null;
        });

    }

    @FXML
    public void onBackup() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Save Backup");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON", "*.json"));

        File file = chooser.showSaveDialog(null);
        if (file == null) return;

        showLoader("Backing up...");

        new Thread(() -> {
            boolean ok = backupManager.backupToFile(file);

            Platform.runLater(() -> {
                //hideLoader();
                if (ok) showToast("Backup completed");
                else showToast("Backup failed");
            });
        }).start();
    }

    @FXML
    public void onRestore() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Select Backup File");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON", "*.json"));

        File file = chooser.showOpenDialog(null);
        if (file == null) return;

        showLoader("Restoring...");

        new Thread(() -> {
            boolean ok = backupManager.restoreFromFile(file);

            Platform.runLater(() -> {
                //hideLoader();
                if (ok) {
                    showToast("Restore completed");
                    try {
                        TransitionUtil.slideFade("main-view.fxml", 300, TransitionUtil.Direction.RIGHT);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    showToast("Restore failed");
                }
            });
        }).start();
    }

    private void showLoader(String message) {
        Stage loaderStage = HelloApplication.currentStage;
        if (loaderStage != null && loaderStage.isShowing()) return;

        ProgressIndicator pi = new ProgressIndicator();
        Label lbl = new Label(message);

        VBox box = new VBox(12, pi, lbl);
        box.setPadding(new Insets(20));
        box.setStyle("-fx-background-color: white; -fx-border-color: #ccc;");
        box.setAlignment(Pos.CENTER);

        loaderStage = new Stage();
        loaderStage.initStyle(StageStyle.UNDECORATED);
        loaderStage.setScene(new Scene(box));
        loaderStage.show();
    }

    private void showToast(String message) {
        Stage toastStage = new Stage();
        toastStage.initStyle(StageStyle.TRANSPARENT);

        Label label = new Label(message);
        label.setStyle("-fx-background-color: black; -fx-text-fill: white; -fx-padding: 10; -fx-background-radius: 6;");

        StackPane root = new StackPane(label);
        root.setStyle("-fx-background-color: transparent;");
        root.setPadding(new Insets(10));

        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);

        toastStage.setScene(scene);
        toastStage.setAlwaysOnTop(true);
        toastStage.show();

        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished(e -> toastStage.close());
        delay.play();
    }

    public void onAddNote() throws IOException {
        pNote = null;
        TransitionUtil.slideFade("add-note.fxml", 300, TransitionUtil.Direction.LEFT);
    }



}
