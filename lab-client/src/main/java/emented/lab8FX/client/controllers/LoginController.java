package emented.lab8FX.client.controllers;

import emented.lab8FX.client.exceptions.ExceptionWithAlert;
import emented.lab8FX.client.models.AuthModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private AuthModel authModel;

    public void setAuthModel(AuthModel authModel) {
        this.authModel = authModel;
    }

    @FXML
    public void loginAction() {
        try {
            authModel.processLogin(usernameField.getText(), passwordField.getText());
        } catch (ExceptionWithAlert e) {
            e.showAlert();
            usernameField.clear();
            passwordField.clear();
        }
    }

    @FXML
    public void registerAction() {
        try {
            authModel.switchToReg();
        } catch (ExceptionWithAlert e) {
            e.showAlert();
        }
    }
}
