package emented.lab8FX.client.controllers;

import emented.lab8FX.client.exceptions.ExceptionWithAlert;
import emented.lab8FX.client.models.ConnectionModel;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

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
