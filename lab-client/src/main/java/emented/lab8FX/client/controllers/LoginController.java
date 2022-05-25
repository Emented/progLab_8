package emented.lab8FX.client.controllers;

import emented.lab8FX.client.exceptions.ExceptionWithAlert;
import emented.lab8FX.client.models.AbstractModel;
import emented.lab8FX.client.models.AuthModel;
import emented.lab8FX.client.util.PathToViews;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController extends AbstractController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private AuthModel authModel;

    @Override
    public void initializeController() {
        authModel = (AuthModel) getModel();
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
            authModel.switchScene(PathToViews.REGISTRATION_VIEW, authModel);
        } catch (ExceptionWithAlert e) {
            e.showAlert();
        }
    }
}
