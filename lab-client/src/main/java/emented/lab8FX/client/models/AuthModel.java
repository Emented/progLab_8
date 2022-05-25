package emented.lab8FX.client.models;

import emented.lab8FX.client.controllers.LoginController;
import emented.lab8FX.client.controllers.MainController;
import emented.lab8FX.client.controllers.RegistrationController;
import emented.lab8FX.client.exceptions.ExceptionWithAlert;
import emented.lab8FX.client.util.ClientSocketWorker;
import emented.lab8FX.client.util.PathToViews;
import emented.lab8FX.client.util.Session;
import emented.lab8FX.common.abstractions.AbstractResponse;
import emented.lab8FX.common.util.requests.LoginRequest;
import emented.lab8FX.common.util.requests.RegisterRequest;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
            throw new IllegalArgumentException();
        }
        try {
            getClientSocketWorker().sendRequest(new LoginRequest("test", username, password));
            AbstractResponse response = super.getClientSocketWorker().receiveResponse();
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
            getClientSocketWorker().sendRequest(new RegisterRequest(username, fPassword, "info"));
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
            switchToMain(session);
        } else {
            throw new ExceptionWithAlert(response.getMessage());
        }
    }

    public void switchToReg() throws ExceptionWithAlert {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(PathToViews.REGISTRATION_VIEW.getPath()));
            RegistrationController registrationController = fxmlLoader.getController();
            registrationController.setAuthModel(this);
            Parent parent = fxmlLoader.load();
            Scene scene = new Scene(parent);
            getCurrentStage().setTitle("Registration menu");
            getCurrentStage().setScene(scene);
            getCurrentStage().sizeToScene();
        } catch (IOException e) {
            throw new ExceptionWithAlert("Troubles during switching to registration!");
        }
    }

    public void switchToLog() throws ExceptionWithAlert {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(PathToViews.LOGIN_VIEW.getPath()));
            LoginController loginController = fxmlLoader.getController();
            loginController.setAuthModel(this);
            Parent parent = fxmlLoader.load();
            Scene scene = new Scene(parent);
            getCurrentStage().setTitle("Login menu");
            getCurrentStage().setScene(scene);
            getCurrentStage().sizeToScene();
        } catch (IOException e) {
            throw new ExceptionWithAlert("Troubles during switching to login!");
        }
    }

    private void switchToMain(Session session) throws ExceptionWithAlert {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(PathToViews.MAIN_VIEW.getPath()));
            MainController mainController = fxmlLoader.getController();
            MainModel mainModel = new MainModel(getClientSocketWorker(), getCurrentStage(), session);
            mainController.setMainModel(mainModel);
            Parent parent = fxmlLoader.load();
            Scene scene = new Scene(parent);
            getCurrentStage().setTitle("Main menu");
            getCurrentStage().setScene(scene);
            getCurrentStage().sizeToScene();
        } catch (IOException e) {
            throw new ExceptionWithAlert("Troubles during switching to main stage!");
        }
    }
}
