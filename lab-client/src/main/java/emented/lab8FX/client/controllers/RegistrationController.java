package emented.lab8FX.client.controllers;

import emented.lab8FX.client.exceptions.ExceptionWithAlert;
import emented.lab8FX.client.models.RegistrationModel;
import emented.lab8FX.client.util.ClientSocketWorker;
import emented.lab8FX.client.util.PathToViews;
import emented.lab8FX.client.util.Session;
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

    private final RegistrationModel registrationModel;

    public RegistrationController(ClientSocketWorker clientSocketWorker) {
        this.registrationModel = new RegistrationModel(clientSocketWorker, getCurrentStage(), this);
    }

    @FXML
    public void loginAction() {
        try {
            switchScene(PathToViews.LOGIN_VIEW, param -> new LoginController(registrationModel.getClientSocketWorker()));
        } catch (ExceptionWithAlert e) {
            e.showAlert();
        }
    }

    @FXML
    public void registerAction() {
        try {
            Session session = registrationModel.processRegistration(usernameField.getText(),
                    firstPasswordField.getText(),
                    secondPasswordField.getText());
            switchScene(PathToViews.MAIN_VIEW, param -> new MainController(registrationModel.getClientSocketWorker(), session));
        } catch (ExceptionWithAlert e) {
            e.showAlert();
            usernameField.clear();
            firstPasswordField.clear();
            secondPasswordField.clear();
        }
    }
}
