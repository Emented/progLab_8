package emented.lab8FX.client.controllers;

import emented.lab8FX.client.exceptions.ExceptionWithAlert;
import emented.lab8FX.client.models.AuthModel;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegistrationController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField firstPasswordField;

    @FXML
    private PasswordField secondPasswordField;
    
    private AuthModel authModel;

    public void setAuthModel(AuthModel authModel) {
        this.authModel = authModel;
    }

    @FXML
    public void loginAction() {
        try {
            authModel.switchToLog();
        } catch (ExceptionWithAlert e) {
            e.showAlert();
        }
    }

    @FXML
    public void registerAction() {
        try {
            authModel.processRegistration(usernameField.getText(), firstPasswordField.getText(), secondPasswordField.getText());
        } catch (ExceptionWithAlert e) {
            e.showAlert();
            usernameField.clear();
            firstPasswordField.clear();
            secondPasswordField.clear();
        }
    }
}
