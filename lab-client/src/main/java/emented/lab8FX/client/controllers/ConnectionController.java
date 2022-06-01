package emented.lab8FX.client.controllers;

import emented.lab8FX.client.exceptions.ExceptionWithAlert;
import emented.lab8FX.client.exceptions.FieldsValidationException;
import emented.lab8FX.client.models.ConnectionModel;
import emented.lab8FX.client.util.ClientSocketWorker;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class ConnectionController extends AbstractController implements Initializable {

    private final ConnectionModel connectionModel;
    @FXML
    private TextField addressField;
    @FXML
    private TextField portField;

    public ConnectionController(ClientSocketWorker clientSocketWorker) {
        connectionModel = new ConnectionModel(clientSocketWorker, getCurrentStage(), this);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setResourceBundle(resources);
        addRegex(portField);
    }

    @FXML
    private void connectAction() {
        List<TextField> textFields = Arrays.asList(addressField, portField);
        removeFieldsColoring(textFields);
        try {
            connectionModel.connect(addressField.getText(),
                    portField.getText());
        } catch (ExceptionWithAlert e) {
            if (e.isFatal()) {
                e.showAlert();
                connectionModel.getClientSocketWorker().closeSocket();
                Platform.exit();
            } else {
                e.showAlert();
                clearFields(textFields);
            }
        } catch (FieldsValidationException e) {
            showFieldsErrors(e.getErrorList(), textFields);
        }
    }
}
