package emented.lab8FX.client.controllers;

import emented.lab8FX.client.util.ClientSocketWorker;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ConnectionController {

    @FXML
    private Button connectButton;

    @FXML
    private TextField addressField;
    @FXML
    private TextField portField;

    @FXML
    private void connectAction() {
        try {
            ClientSocketWorker clientSocketWorker = new ClientSocketWorker();
            String address = addressField.getText();
            String port = portField.getText();
            if (!address.equals("")) {
                clientSocketWorker.setAddress(address);
            }
            if (!port.equals("")) {
                int portNum = Integer.parseInt(port);
                if (portNum > 0 && portNum <= 65535) {
                    clientSocketWorker.setPort(portNum);
                } else {
                    throw new IllegalArgumentException();
                }
            }
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/registration.fxml"));
            fxmlLoader.setControllerFactory(controllerClass -> new RegistrationController(clientSocketWorker));
            Parent parent = fxmlLoader.load();
            Stage currentStage = (Stage) connectButton.getScene().getWindow();
            Scene scene = new Scene(parent);
            currentStage.setTitle("Registration menu");
            currentStage.setScene(scene);
            currentStage.sizeToScene();
            currentStage.setMinWidth(450);
            currentStage.setMinHeight(397);
        } catch (IllegalArgumentException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Wrong port! It should be a number between 1 and 65535");
            alert.showAndWait();
            addressField.clear();
            portField.clear();
        } catch (UnknownHostException | SocketException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Some troubles with connection, try again later!");
            alert.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
