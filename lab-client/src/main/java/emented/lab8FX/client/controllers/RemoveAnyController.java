package emented.lab8FX.client.controllers;

import emented.lab8FX.client.exceptions.ExceptionWithAlert;
import emented.lab8FX.client.exceptions.FieldsValidationException;
import emented.lab8FX.client.models.MainModel;
import emented.lab8FX.client.models.RemoveAnyModel;
import emented.lab8FX.client.util.ClientSocketWorker;
import emented.lab8FX.client.util.Session;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.util.List;

public class RemoveAnyController extends AbstractController {
    private final RemoveAnyModel removeAnyModel;

    private final MainModel mainModel;
    @FXML
    private TextField numberField;

    public RemoveAnyController(ClientSocketWorker clientSocketWorker, Session session, MainModel mainModel) {
        removeAnyModel = new RemoveAnyModel(clientSocketWorker, getCurrentStage(), session, this);
        this.mainModel = mainModel;
    }

    public void initialize() {
        addRegex(numberField);
    }

    @FXML
    public void removeAction() {
        List<TextField> textFields = List.of(numberField);
        removeFieldsColoring(textFields);
        try {
            Alert alert = removeAnyModel.processRemove(numberField.getText());
            if (alert.getAlertType().equals(Alert.AlertType.INFORMATION)) {
                alert.showAndWait();
                mainModel.getNewCollection();
                getCurrentStage().close();
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

