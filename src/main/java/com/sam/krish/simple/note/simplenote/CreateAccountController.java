package com.sam.krish.simple.note.simplenote;

import com.sam.krish.simple.note.simplenote.helper.AlertDialogHelper;
import com.sam.krish.simple.note.simplenote.preference.UserPreference;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class CreateAccountController {

    @FXML
    private StackPane root;
    @FXML
    private ImageView bgImg;

    @FXML
    private TextField username;
    @FXML
    private TextField childhoodName;
    @FXML
    private PasswordField password;

    @FXML
    public void initialize() {
        bgImg.fitWidthProperty().bind(root.widthProperty());
        bgImg.fitHeightProperty().bind(root.heightProperty());


    }


    @FXML
    public void onCreateAccount() throws IOException {


        String name = username.getText();
        String cName = childhoodName.getText();
        String pass = password.getText();


        UserPreference userPreference = new UserPreference();

        if (name.length() >= 6 && pass.length() >= 8 && cName.length() >= 3) {
            userPreference.setUsername(name);
            userPreference.setPassword(pass);
            userPreference.setChildHoodName(cName);
            AlertDialogHelper.SuccessDialog("Account created successfully!");
            TransitionUtil.slideFade("login-info.fxml", 300, TransitionUtil.Direction.RIGHT);
        } else {

            AlertDialogHelper.ErrorDialog("""
                    •Name must have at least 6 char!
                    •Password must have a least 8 char!
                    •Childhood name can not by empty!
                    """);

        }


    }

    @FXML
    public void onCancelAccount() throws IOException {
        TransitionUtil.slideFade("login-info.fxml", 200, TransitionUtil.Direction.RIGHT);

    }

}
