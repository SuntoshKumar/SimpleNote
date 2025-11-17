package com.sam.krish.simple.note.simplenote;

import com.sam.krish.simple.note.simplenote.helper.AlertDialogHelper;
import com.sam.krish.simple.note.simplenote.preference.UserPref;
import com.sam.krish.simple.note.simplenote.preference.UserPreference;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import net.synedra.validatorfx.Check;

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
    CheckBox checkBox;

    UserPreference preference;

    @FXML
    public void initialize() {
        bgImg.fitWidthProperty().bind(root.widthProperty());
        bgImg.fitHeightProperty().bind(root.heightProperty());

        preference = new UserPreference();

        checkBox.selectedProperty().addListener((obs, oldVal, newVal) -> {
            String name = username.getText();
            String pass = password.getText();

            String prefName = preference.getUserName();
            String prefPass = preference.getPassword();

            if (name.equals(prefName) && pass.equals(prefPass)) {
                preference.setRememberMe(newVal);
            }
        });

    }

    @FXML
    public void onClickSignIn() throws IOException {

        String name = username.getText();
        String pass = password.getText();

        System.out.println("Login2 "+preference.getRememberMe());

        String prefName = preference.getUserName();
        String prefPass = preference.getPassword();

        if (name.equals(prefName) && pass.equals(prefPass)){
            TransitionUtil.slideFade("main-view.fxml", 200, TransitionUtil.Direction.LEFT);
        }else {
            AlertDialogHelper.ErrorDialog("""
                    Username or Password is not correct!
                    """);
        }




    }

    public void onClickCreateAccount() throws IOException {
        TransitionUtil.slideFade("create-account.fxml", 200, TransitionUtil.Direction.LEFT);
    }


}
