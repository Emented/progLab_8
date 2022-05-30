package emented.lab8FX.client.controllers;

import emented.lab8FX.client.exceptions.ExceptionWithAlert;
import emented.lab8FX.client.exceptions.FieldsValidationException;
import emented.lab8FX.client.models.CountModel;
import emented.lab8FX.client.util.ClientSocketWorker;
import emented.lab8FX.client.util.Session;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.util.List;

public class CountController extends AbstractController {

    private final CountModel countModel;
    @FXML
    public TextField numberField;

    public CountController(ClientSocketWorker clientSocketWorker, Session session) {
        countModel = new CountModel(clientSocketWorker, getCurrentStage(), session, this);
    }

    public void initialize() {
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
