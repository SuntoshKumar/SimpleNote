package com.sam.krish.simple.note.simplenote;

import com.sam.krish.simple.note.simplenote.helper.AlertDialogHelper;
import com.sam.krish.simple.note.simplenote.preference.UserPreference;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class LoginController {

    @FXML
    private StackPane root;
    @FXML private ImageView bgImg;

    @FXML
    TextField username;
    @FXML
    PasswordField password;

    @FXML
    public void initialize() {
        bgImg.fitWidthProperty().bind(root.widthProperty());
        bgImg.fitHeightProperty().bind(root.heightProperty());
    }

    @FXML
    public void onClickSignIn() throws IOException {

//        String name = username.getText();
//        String pass = password.getText();
//
//        UserPreference preference = new UserPreference();
//
//        String prefName = preference.getUserName();
//        String prefPass = preference.getPassword();
//
//        if (name.equals(prefName) && pass.equals(prefPass)){
//            TransitionUtil.slideFade("main-view.fxml", 200, TransitionUtil.Direction.LEFT);
//        }else {
//            AlertDialogHelper.ErrorDialog("""
//                    Username or Password is not correct!
//                    """);
//        }

        TransitionUtil.slideFade("main-view.fxml", 200, TransitionUtil.Direction.LEFT);


    }

    public void onClickCreateAccount() throws IOException {
        TransitionUtil.slideFade("create-account.fxml", 200, TransitionUtil.Direction.LEFT);
    }


}
