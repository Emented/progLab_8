package emented.lab8FX.client.controllers;

import emented.lab8FX.client.exceptions.ExceptionWithAlert;
import emented.lab8FX.client.exceptions.FieldsValidationException;
import emented.lab8FX.client.models.LoginModel;
import emented.lab8FX.client.util.ClientSocketWorker;
import emented.lab8FX.client.util.PathToViews;
import emented.lab8FX.client.util.Session;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class LoginController extends AbstractController implements Initializable {
    private final LoginModel loginModel;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    public LoginController(ClientSocketWorker clientSocketWorker) {
        this.loginModel = new LoginModel(clientSocketWorker, getCurrentStage(), this);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setResourceBundle(resources);
    }

    @FXML
    public void loginAction() {
        List<TextField> textFields = Arrays.asList(usernameField, passwordField);
        removeFieldsColoring(textFields);
        try {
            Session session = loginModel.processLogin(usernameField.getText(),
                    passwordField.getText());
            switchScene(PathToViews.MAIN_VIEW, param -> new MainController(loginModel.getClientSocketWorker(), session), getResourceBundle());
        } catch (ExceptionWithAlert e) {
            e.showAlert();
            clearFields(textFields);
        } catch (FieldsValidationException e) {
            showFieldsErrors(e.getErrorList(), textFields);
        }
    }

    @FXML
    public void registerAction() {
        try {
            switchScene(PathToViews.REGISTRATION_VIEW, param -> new RegistrationController(loginModel.getClientSocketWorker()), getResourceBundle());
        } catch (ExceptionWithAlert e) {
            e.showAlert();
        }
    }
}
