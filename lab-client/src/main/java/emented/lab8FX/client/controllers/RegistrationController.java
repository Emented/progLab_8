package emented.lab8FX.client.controllers;

import emented.lab8FX.client.exceptions.ExceptionWithAlert;
import emented.lab8FX.client.models.AuthModel;
import emented.lab8FX.client.util.PathToViews;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegistrationController extends AbstractController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField firstPasswordField;

    @FXML
    private PasswordField secondPasswordField;



    @FXML
    public void loginAction() {
        try {
            AuthModel authModel = (AuthModel) getModel();
            authModel.switchScene(PathToViews.LOGIN_VIEW, authModel);
        } catch (ExceptionWithAlert e) {
            e.showAlert();
        }
    }

    @FXML
    public void registerAction() {
        try {
            AuthModel authModel = (AuthModel) getModel();
            authModel.processRegistration(usernameField.getText(), firstPasswordField.getText(), secondPasswordField.getText());
        } catch (ExceptionWithAlert e) {
            e.showAlert();
            usernameField.clear();
            firstPasswordField.clear();
            secondPasswordField.clear();
        }
    }
}
