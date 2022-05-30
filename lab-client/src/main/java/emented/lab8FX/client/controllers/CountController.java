package emented.lab8FX.client.controllers;

import emented.lab8FX.client.exceptions.ExceptionWithAlert;
import emented.lab8FX.client.exceptions.FieldsValidationException;
import emented.lab8FX.client.models.CountModel;
import emented.lab8FX.client.util.ClientSocketWorker;
import emented.lab8FX.client.util.Session;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CountController extends AbstractController implements Initializable {

    private final CountModel countModel;
    @FXML
    public TextField numberField;

    public CountController(ClientSocketWorker clientSocketWorker, Session session) {
        countModel = new CountModel(clientSocketWorker, getCurrentStage(), session, this);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setResourceBundle(resources);
        addRegex(numberField);
    }

    public void setField(Long number) {
        numberField.setText(number.toString());
    }

    @FXML
    public void countAction() {
        List<TextField> textFields = List.of(numberField);
        removeFieldsColoring(textFields);
        try {
            Alert alert = countModel.processCount(numberField.getText());
            if (alert.getAlertType().equals(Alert.AlertType.INFORMATION)) {
                alert.showAndWait();
            } else {
                alert.showAndWait();
            }
        } catch (FieldsValidationException e) {
            showFieldsErrors(e.getErrorList(), textFields);
        } catch (ExceptionWithAlert e) {
            e.showAlert();
        }
    }
}
