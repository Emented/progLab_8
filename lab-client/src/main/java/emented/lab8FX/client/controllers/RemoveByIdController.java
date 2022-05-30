package emented.lab8FX.client.controllers;

import emented.lab8FX.client.exceptions.ExceptionWithAlert;
import emented.lab8FX.client.exceptions.FieldsValidationException;
import emented.lab8FX.client.models.MainModel;
import emented.lab8FX.client.models.RemoveByIdModel;
import emented.lab8FX.client.util.ClientSocketWorker;
import emented.lab8FX.client.util.Session;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class RemoveByIdController extends AbstractController implements Initializable {

    private final RemoveByIdModel removeByIdModel;

    private final MainModel mainModel;
    @FXML
    private TextField idField;

    public RemoveByIdController(ClientSocketWorker clientSocketWorker, Session session, MainModel mainModel) {
        removeByIdModel = new RemoveByIdModel(clientSocketWorker, getCurrentStage(), session, this);
        this.mainModel = mainModel;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setResourceBundle(resources);
        addRegex(idField);
    }

    public void setField(Long id) {
        idField.setText(id.toString());
    }

    @FXML
    public void removeAction() {
        List<TextField> textFields = List.of(idField);
        removeFieldsColoring(textFields);
        try {
            Alert alert = removeByIdModel.processRemove(idField.getText());
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
