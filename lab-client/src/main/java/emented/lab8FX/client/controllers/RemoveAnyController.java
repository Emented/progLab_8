package emented.lab8FX.client.controllers;

import emented.lab8FX.client.exceptions.ExceptionWithAlert;
import emented.lab8FX.client.exceptions.FieldsValidationException;
import emented.lab8FX.client.models.MainModel;
import emented.lab8FX.client.models.RemoveAnyModel;
import emented.lab8FX.client.util.ClientSocketWorker;
import emented.lab8FX.client.util.Session;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class RemoveAnyController extends AbstractController implements Initializable {
    private final RemoveAnyModel removeAnyModel;

    private final MainModel mainModel;
    @FXML
    private TextField numberField;

    public RemoveAnyController(ClientSocketWorker clientSocketWorker, Session session, MainModel mainModel) {
        removeAnyModel = new RemoveAnyModel(clientSocketWorker, getCurrentStage(), session, this);
        this.mainModel = mainModel;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setResourceBundle(resources);
        addRegex(numberField);
    }

    public MainModel getMainModel() {
        return mainModel;
    }

    @FXML
    public void removeAction() {
        List<TextField> textFields = List.of(numberField);
        removeFieldsColoring(textFields);
        try {
            removeAnyModel.processRemove(numberField.getText());
            getCurrentStage().close();
        } catch (FieldsValidationException e) {
            showFieldsErrors(e.getErrorList(), textFields);
        }
    }
}

