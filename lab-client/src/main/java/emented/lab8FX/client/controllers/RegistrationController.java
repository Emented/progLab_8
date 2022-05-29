package emented.lab8FX.client.controllers;

import emented.lab8FX.client.exceptions.ExceptionWithAlert;
import emented.lab8FX.client.exceptions.FieldsValidationException;
import emented.lab8FX.client.models.RegistrationModel;
import emented.lab8FX.client.util.ClientSocketWorker;
import emented.lab8FX.client.util.PathToViews;
import emented.lab8FX.client.util.Session;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.Arrays;
import java.util.List;

public class RegistrationController extends AbstractController {
    @FXML private TextField usernameField;
    @FXML private PasswordField firstPasswordField;
    @FXML private PasswordField secondPasswordField;

    private final RegistrationModel registrationModel;

    public RegistrationController(ClientSocketWorker clientSocketWorker) {
        this.registrationModel = new RegistrationModel(clientSocketWorker, getCurrentStage(), this);
    }

    @FXML public void loginAction() {
        try {
            switchScene(PathToViews.LOGIN_VIEW, param -> new LoginController(registrationModel.getClientSocketWorker()));
        } catch (ExceptionWithAlert e) {
            e.showAlert();
        }
    }

    @FXML public void registerAction() {
        String username = usernameField.getText();
        String fPassword = firstPasswordField.getText();
        String sPassword = secondPasswordField.getText();
        List<TextField> textFields = Arrays.asList(usernameField, firstPasswordField, secondPasswordField);
        removeFieldsColoring(textFields);
        try {
            Session session = registrationModel.processRegistration(username,
                    fPassword,
                    sPassword);
            switchScene(PathToViews.MAIN_VIEW, param -> new MainController(registrationModel.getClientSocketWorker(), session));
        } catch (ExceptionWithAlert e) {
            e.showAlert();
            clearFields(textFields);
        } catch (FieldsValidationException e) {
            showFieldsErrors(e.getErrorList(), textFields);
        }
    }
}
