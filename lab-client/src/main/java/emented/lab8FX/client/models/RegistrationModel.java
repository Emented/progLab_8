package emented.lab8FX.client.models;

import emented.lab8FX.client.controllers.RegistrationController;
import emented.lab8FX.client.exceptions.ExceptionWithAlert;
import emented.lab8FX.client.exceptions.FieldsValidationException;
import emented.lab8FX.client.util.ClientSocketWorker;
import emented.lab8FX.client.util.Session;
import emented.lab8FX.client.util.validators.UserValidotor;
import emented.lab8FX.common.abstractions.AbstractResponse;
import emented.lab8FX.common.util.requests.RegisterRequest;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.Objects;

public class RegistrationModel extends AbstractModel {

    private final RegistrationController currentController;

    public RegistrationModel(ClientSocketWorker clientSocketWorker, Stage currentStage, RegistrationController currentController) {
        super(clientSocketWorker, currentStage);
        this.currentController = currentController;
    }

    public Session processRegistration(String username, String fPassword, String sPassword) throws ExceptionWithAlert, FieldsValidationException {
        List<String> errorList = UserValidotor.validateRegisterUser(username, fPassword, sPassword);
        if (errorList.stream().anyMatch(Objects::nonNull)) {
            throw new FieldsValidationException(errorList);
        }
        try {
            AbstractResponse response = getClientSocketWorker().proceedTransaction(new RegisterRequest(username, fPassword, getClientInfo()));
            if (response.isSuccess()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, response.getMessage());
                alert.setHeaderText(null);
                alert.showAndWait();
                return new Session(username, fPassword);
            } else {
                throw new ExceptionWithAlert(response.getMessage());
            }
        } catch (SocketTimeoutException e) {
            throw new ExceptionWithAlert(currentController.getResourceBundle().getString("connection_exception.time"), true);
        } catch (IOException e) {
            throw new ExceptionWithAlert(currentController.getResourceBundle().getString("connection_exception.connection"));
        } catch (ClassNotFoundException e) {
            throw new ExceptionWithAlert(currentController.getResourceBundle().getString("connection_exception.response"));
        }
    }
}
