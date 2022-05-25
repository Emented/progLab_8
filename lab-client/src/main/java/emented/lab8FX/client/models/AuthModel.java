package emented.lab8FX.client.models;

import emented.lab8FX.client.exceptions.ExceptionWithAlert;
import emented.lab8FX.client.util.ClientSocketWorker;
import emented.lab8FX.client.util.PathToViews;
import emented.lab8FX.client.util.Session;
import emented.lab8FX.common.abstractions.AbstractResponse;
import emented.lab8FX.common.util.requests.LoginRequest;
import emented.lab8FX.common.util.requests.RegisterRequest;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class AuthModel extends AbstractModel {
    public AuthModel(ClientSocketWorker clientSocketWorker, Stage currentStage) {
        super(clientSocketWorker, currentStage);
    }

    public void processLogin(String username, String password) throws ExceptionWithAlert {
        if (username.length() < 5 || password.length() < 5) {
            throw new ExceptionWithAlert("Login and password should be longer that 5 symbols");
        }
        try {
            getClientSocketWorker().sendRequest(new LoginRequest("test", username, password));
            AbstractResponse response = getClientSocketWorker().receiveResponse();
            checkAuthResponse(username, password, response);
        } catch (IOException e) {
            throw new ExceptionWithAlert("Some troubles during sending request!");
        } catch (ClassNotFoundException e) {
            throw new ExceptionWithAlert("Response came damaged!");
        }
    }

    public void processRegistration(String username, String fPassword, String sPassword) throws ExceptionWithAlert {
        if (username.length() < 5 || fPassword.length() < 5) {
            throw new ExceptionWithAlert("Login and password should be longer that 5 symbols");
        }
        if (!Objects.equals(fPassword, sPassword)) {
            throw new ExceptionWithAlert("Passwords mismatch");
        }
        try {
            getClientSocketWorker().sendRequest(new RegisterRequest("test", username, fPassword));
            AbstractResponse response = getClientSocketWorker().receiveResponse();
            checkAuthResponse(username, fPassword, response);
        } catch (IOException e) {
            throw new ExceptionWithAlert("Some troubles during sending request!");
        } catch (ClassNotFoundException e) {
            throw new ExceptionWithAlert("Response came damaged!");
        }
    }

    private void checkAuthResponse(String username, String password, AbstractResponse response) throws ExceptionWithAlert {
        if (response.isSuccess()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, response.getMessage());
            alert.setHeaderText(null);
            alert.showAndWait();
            Session session = new Session(username, password);
            MainModel mainModel = new MainModel(getClientSocketWorker(), getCurrentStage(), session);
//            switchScene(PathToViews.MAIN_VIEW, mainModel);
        } else {
            throw new ExceptionWithAlert(response.getMessage());
        }
    }
}
