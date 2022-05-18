package emented.lab8FX.client.controllers;

import emented.lab8FX.client.util.ClientSocketWorker;
import emented.lab8FX.common.abstractions.AbstractResponse;
import emented.lab8FX.common.util.requests.RegisterRequest;
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

public class RegistrationController {
    private final ClientSocketWorker clientSocketWorker;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField firstPasswordField;
    @FXML
    private PasswordField secondPasswordField;
    @FXML
    private Button loginButton;

    public RegistrationController(ClientSocketWorker clientSocketWorker) {
        this.clientSocketWorker = clientSocketWorker;
    }

    @FXML
    public void loginAction() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
            fxmlLoader.setControllerFactory(controllerClass -> new LoginController(clientSocketWorker));
            Parent parent = fxmlLoader.load();
            Stage currentStage = (Stage) loginButton.getScene().getWindow();
            Scene scene = new Scene(parent);
            currentStage.setTitle("Login menu");
            currentStage.setScene(scene);
            currentStage.sizeToScene();
            currentStage.setHeight(315);
            currentStage.setMinWidth(450);
            currentStage.setMinHeight(315);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void registerAction() {
        String username = usernameField.getText();
        String firstPassword = firstPasswordField.getText();
        String secondPassword = secondPasswordField.getText();
        if (username.length() < 5 || firstPassword.length() < 5) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Login and password should be longer that 5 symbols");
            alert.showAndWait();
            usernameField.clear();
            firstPasswordField.clear();
            secondPasswordField.clear();
            return;
        }
        if (!Objects.equals(firstPassword, secondPassword)) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Passwords mismatch");
            alert.showAndWait();
            firstPasswordField.clear();
            secondPasswordField.clear();
            return;
        }
        try {
            clientSocketWorker.sendRequest(new RegisterRequest(username, firstPassword, "info"));
            AbstractResponse response = clientSocketWorker.receiveResponse();
            if (response.isSuccess()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, response.getMessage());
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, response.getMessage());
                alert.showAndWait();
            }
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, e.getMessage());
            alert.showAndWait();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
