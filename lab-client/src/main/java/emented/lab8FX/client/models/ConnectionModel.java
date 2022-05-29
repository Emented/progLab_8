package emented.lab8FX.client.models;

import emented.lab8FX.client.controllers.ConnectionController;
import emented.lab8FX.client.controllers.RegistrationController;
import emented.lab8FX.client.exceptions.ExceptionWithAlert;
import emented.lab8FX.client.exceptions.FieldsValidationException;
import emented.lab8FX.client.util.ClientSocketWorker;
import emented.lab8FX.client.util.PathToViews;
import emented.lab8FX.client.util.validators.ConnectionValidator;
import emented.lab8FX.common.abstractions.AbstractResponse;
import emented.lab8FX.common.util.requests.ConnectionRequest;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class ConnectionModel extends AbstractModel {

    private final ConnectionController currentController;

    public ConnectionModel(ClientSocketWorker clientSocketWorker, Stage currentStage, ConnectionController connectionController) {
        super(clientSocketWorker, currentStage);
        this.currentController = connectionController;
    }


    public void connect(String address, String port) throws ExceptionWithAlert, FieldsValidationException {
        List<String> errorList = ConnectionValidator.validateConnection(address, port);
        if (errorList.stream().anyMatch(Objects::nonNull)) {
            throw new FieldsValidationException(errorList);
        }
        try {
            String currentAddress = ConnectionValidator.validateAddress(address);
            Integer currentPort = ConnectionValidator.validatePort(port);
            if (currentAddress != null) {
                getClientSocketWorker().setAddress(currentAddress);
            }
            if (currentPort != null) {
                getClientSocketWorker().setPort(currentPort);
            }
            AbstractResponse response = getClientSocketWorker().proceedTransaction(new ConnectionRequest(getClientInfo()));
            if (response.isSuccess()) {
                currentController.switchScene(PathToViews.REGISTRATION_VIEW, comp -> new RegistrationController(getClientSocketWorker()));
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new ExceptionWithAlert("Some troubles with connection, try again later!");
        }
    }
}
