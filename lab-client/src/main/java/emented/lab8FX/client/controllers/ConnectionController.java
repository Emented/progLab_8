package emented.lab8FX.client.controllers;

import emented.lab8FX.client.exceptions.ExceptionWithAlert;
import emented.lab8FX.client.exceptions.FieldsValidationException;
import emented.lab8FX.client.models.ConnectionModel;
import emented.lab8FX.client.util.ClientSocketWorker;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

public class ConnectionController extends AbstractController {

    @FXML private TextField addressField;
    @FXML private TextField portField;

    private final ConnectionModel connectionModel;

    public ConnectionController(ClientSocketWorker clientSocketWorker) {
        connectionModel = new ConnectionModel(clientSocketWorker, getCurrentStage(), this);
    }

    public void initialize() {
    }

    @FXML private void connectAction() {
        String host = addressField.getText();
        String port = portField.getText();
        List<TextField> textFields = Arrays.asList(addressField, portField);
        removeFieldsColoring(textFields);
        try {
            connectionModel.connect(host, port);
        } catch (ExceptionWithAlert e) {
            e.showAlert();
            clearFields(textFields);
        } catch (FieldsValidationException e) {
            showFieldsErrors(e.getErrorList(), textFields);
        }
    }
}
