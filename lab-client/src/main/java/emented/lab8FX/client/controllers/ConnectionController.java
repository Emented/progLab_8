package emented.lab8FX.client.controllers;

import emented.lab8FX.client.exceptions.ExceptionWithAlert;
import emented.lab8FX.client.models.AbstractModel;
import emented.lab8FX.client.models.ConnectionModel;
import emented.lab8FX.client.util.ClientSocketWorker;
import emented.lab8FX.common.util.requests.ConnectionRequest;
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

public class ConnectionController extends AbstractController {

    @FXML
    private TextField addressField;
    
    @FXML
    private TextField portField;

    private ConnectionModel connectionModel;

    @Override
    public void initializeController() {
        connectionModel = (ConnectionModel) getModel();
    }

    @FXML
    private void connectAction() {
        String host = addressField.getText();
        String port = portField.getText();
        try {
            connectionModel.connect(host, port);
        } catch (ExceptionWithAlert e) {
            e.showAlert();
            addressField.clear();
            portField.clear();
        }
    }
}
