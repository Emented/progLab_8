package emented.lab8FX.client.models;

import emented.lab8FX.client.controllers.LoginController;
import emented.lab8FX.client.exceptions.ExceptionWithAlert;
import emented.lab8FX.client.exceptions.FieldsValidationException;
import emented.lab8FX.client.util.ClientSocketWorker;
import emented.lab8FX.client.util.Session;
import emented.lab8FX.client.util.validators.ConnectionValidator;
import emented.lab8FX.client.util.validators.UserValidotor;
import emented.lab8FX.common.abstractions.AbstractResponse;
import emented.lab8FX.common.util.requests.LoginRequest;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class LoginModel extends AbstractModel {

    private final LoginController currentController;

    public LoginModel(ClientSocketWorker clientSocketWorker, Stage currentStage, LoginController loginController) {
        super(clientSocketWorker, currentStage);
        this.currentController = loginController;
    }

    public Session processLogin(String username, String password) throws ExceptionWithAlert, FieldsValidationException {
        List<String> errorList = UserValidotor.validateLoginUser(username, password);
        if (errorList.stream().anyMatch(Objects::nonNull)) {
            throw new FieldsValidationException(errorList);
        }
        try {
            AbstractResponse response = getClientSocketWorker().proceedTransaction(new LoginRequest(username, password, getClientInfo()));
            if (response.isSuccess()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, response.getMessage());
                alert.setHeaderText(null);
                alert.showAndWait();
                return new Session(username, password);
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
