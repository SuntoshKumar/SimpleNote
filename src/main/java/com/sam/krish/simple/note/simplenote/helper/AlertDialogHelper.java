package com.sam.krish.simple.note.simplenote.helper;

import javafx.scene.control.Alert;

public class AlertDialogHelper {

    public static void ErrorDialog(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.show();
    }

    public static void InfoDialog(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Alert!");
        alert.setContentText(message);
        alert.show();
    }

    public static void SuccessDialog(String message){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Success");
        alert.setContentText(message);
        alert.show();
    }

}
