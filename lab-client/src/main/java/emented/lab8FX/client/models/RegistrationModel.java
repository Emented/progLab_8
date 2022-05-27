package emented.lab8FX.client.models;

import emented.lab8FX.client.controllers.RegistrationController;
import emented.lab8FX.client.exceptions.ExceptionWithAlert;
import emented.lab8FX.client.util.ClientSocketWorker;
import emented.lab8FX.client.util.Session;
import emented.lab8FX.common.abstractions.AbstractResponse;
import emented.lab8FX.common.util.requests.RegisterRequest;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class RegistrationModel extends AbstractModel {

    private final RegistrationController currentController;

    public RegistrationModel(ClientSocketWorker clientSocketWorker, Stage currentStage, RegistrationController registrationController) {
        super(clientSocketWorker, currentStage);
        this.currentController = registrationController;
    }

    public Session processRegistration(String username, String fPassword, String sPassword) throws ExceptionWithAlert {
        if (username.length() < 5 || fPassword.length() < 5) {
            throw new ExceptionWithAlert("Login and password should be longer that 5 symbols");
        }
        if (!Objects.equals(fPassword, sPassword)) {
            throw new ExceptionWithAlert("Passwords mismatch");
        }
        try {
            getClientSocketWorker().sendRequest(new RegisterRequest(username, fPassword, getClientInfo()));
            AbstractResponse response = getClientSocketWorker().receiveResponse();
            if (response.isSuccess()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, response.getMessage());
                alert.setHeaderText(null);
                alert.showAndWait();
                return new Session(username, fPassword);
            } else {
                throw new ExceptionWithAlert(response.getMessage());
            }
        } catch (IOException e) {
            throw new ExceptionWithAlert("Some troubles with connection, try again later!");
        } catch (ClassNotFoundException e) {
            throw new ExceptionWithAlert("Response came damaged!");
        }
    }
}
