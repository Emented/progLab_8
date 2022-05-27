package emented.lab8FX.client.models;

import emented.lab8FX.client.controllers.LoginController;
import emented.lab8FX.client.exceptions.ExceptionWithAlert;
import emented.lab8FX.client.util.ClientSocketWorker;
import emented.lab8FX.client.util.Session;
import emented.lab8FX.common.abstractions.AbstractResponse;
import emented.lab8FX.common.util.requests.LoginRequest;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginModel extends AbstractModel {

    private final LoginController currentController;

    public LoginModel(ClientSocketWorker clientSocketWorker, Stage currentStage, LoginController loginController) {
        super(clientSocketWorker, currentStage);
        this.currentController = loginController;
    }

    public Session processLogin(String username, String password) throws ExceptionWithAlert {
        if (username.length() < 5 || password.length() < 5) {
            throw new ExceptionWithAlert("Login and password should be longer that 5 symbols!");
        }
        try {
            getClientSocketWorker().sendRequest(new LoginRequest(username, password, getClientInfo()));
            AbstractResponse response = getClientSocketWorker().receiveResponse();
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
