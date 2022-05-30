package emented.lab8FX.client.controllers;

import emented.lab8FX.client.exceptions.ExceptionWithAlert;
import emented.lab8FX.client.exceptions.FieldsValidationException;
import emented.lab8FX.client.models.RegistrationModel;
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

public class RegistrationController extends AbstractController implements Initializable {
    private final RegistrationModel registrationModel;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField firstPasswordField;
    @FXML
    private PasswordField secondPasswordField;

    public RegistrationController(ClientSocketWorker clientSocketWorker) {
        this.registrationModel = new RegistrationModel(clientSocketWorker, getCurrentStage(), this);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setResourceBundle(resources);
    }

    @FXML
    public void loginAction() {
        try {
            switchScene(PathToViews.LOGIN_VIEW, param -> new LoginController(registrationModel.getClientSocketWorker()), getResourceBundle());
        } catch (ExceptionWithAlert e) {
            e.showAlert();
        }
    }

    @FXML
    public void registerAction() {
        List<TextField> textFields = Arrays.asList(usernameField, firstPasswordField, secondPasswordField);
        removeFieldsColoring(textFields);
        try {
            Session session = registrationModel.processRegistration(usernameField.getText(),
                    firstPasswordField.getText(),
                    secondPasswordField.getText());
            switchScene(PathToViews.MAIN_VIEW, param -> new MainController(registrationModel.getClientSocketWorker(), session), getResourceBundle());
        } catch (ExceptionWithAlert e) {
            e.showAlert();
            clearFields(textFields);
        } catch (FieldsValidationException e) {
            showFieldsErrors(e.getErrorList(), textFields);
        }
    }
}
