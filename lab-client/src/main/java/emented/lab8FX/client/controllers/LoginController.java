package emented.lab8FX.client.controllers;

import emented.lab8FX.client.util.ClientSocketWorker;
import emented.lab8FX.common.abstractions.AbstractResponse;
import emented.lab8FX.common.util.Request;
import emented.lab8FX.common.util.RequestType;
import emented.lab8FX.common.util.Response;
import emented.lab8FX.common.util.requests.LoginRequest;
import emented.lab8FX.common.util.responses.AuthResponse;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button registrationButton;

    private final ClientSocketWorker clientSocketWorker;

    public LoginController(ClientSocketWorker clientSocketWorker) {
        this.clientSocketWorker = clientSocketWorker;
    }

    @FXML
    public void loginAction() {
        String username = usernameField.getText();
        String firstPassword = passwordField.getText();
        if (username.length() < 5 || firstPassword.length() < 5) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Login and password should be longer that 5 symbols");
            alert.showAndWait();
            usernameField.clear();
            passwordField.clear();
            return;
        }
        try {
            clientSocketWorker.sendRequest(new LoginRequest(username, firstPassword, "info"));
            AbstractResponse response = clientSocketWorker.receiveResponse();
            if (response.isSuccess()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, response.getMessage());
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, response.getMessage());
                alert.showAndWait();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void registerAction() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/registration.fxml"));
            fxmlLoader.setControllerFactory(controllerClass -> new RegistrationController(clientSocketWorker));
            Parent parent = fxmlLoader.load();
            Stage currentStage = (Stage) registrationButton.getScene().getWindow();
            Scene scene = new Scene(parent);
            currentStage.setTitle("Registration menu");
            currentStage.setScene(scene);
            currentStage.sizeToScene();
            currentStage.setMinWidth(450);
            currentStage.setMinHeight(397);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
