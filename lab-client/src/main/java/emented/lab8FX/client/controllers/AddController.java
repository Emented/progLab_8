package emented.lab8FX.client.controllers;

import emented.lab8FX.client.exceptions.ExceptionWithAlert;
import emented.lab8FX.client.exceptions.FieldsValidationException;
import emented.lab8FX.client.models.AddModel;
import emented.lab8FX.client.models.MainModel;
import emented.lab8FX.client.util.ClientSocketWorker;
import emented.lab8FX.client.util.Session;
import emented.lab8FX.common.entities.enums.MusicGenre;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AddController extends AbstractController {

    private final AddModel addModel;

    private final MainModel mainModel;
    @FXML
    private TextField nameField;
    @FXML
    private TextField xField;
    @FXML
    private TextField yField;
    @FXML
    private TextField numberField;
    @FXML
    private ChoiceBox<MusicGenre> genreBox;
    @FXML
    private TextField descriptionField;
    @FXML
    private TextField addressField;

    public AddController(ClientSocketWorker clientSocketWorker, Session session, MainModel mainModel) {
        addModel = new AddModel(clientSocketWorker, getCurrentStage(), session, this);
        this.mainModel = mainModel;
    }

    public void initialize() {
        genreBox.setItems(FXCollections.observableArrayList(Stream.of(MusicGenre.values()).collect(Collectors.toList())));
        addRegex(xField, yField, numberField);
    }


    @FXML
    public void addAction() {
        List<TextField> textFields = Arrays.asList(nameField, xField, yField, numberField, descriptionField, addressField);
        removeFieldsColoring(textFields);
        try {
            Alert alert = addModel.processAdd(nameField.getText(),
                    xField.getText(),
                    yField.getText(),
                    numberField.getText(),
                    genreBox.getValue(),
                    descriptionField.getText(),
                    addressField.getText());
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

    @FXML
    public void addIfMaxAction() {
        List<TextField> textFields = Arrays.asList(nameField, xField, yField, numberField, descriptionField, addressField);
        removeFieldsColoring(textFields);
        try {
            Alert alert = addModel.processAddIfMax(nameField.getText(),
                    xField.getText(),
                    yField.getText(),
                    numberField.getText(),
                    genreBox.getValue(),
                    descriptionField.getText(),
                    addressField.getText());
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

    public void clearGenreAction() {
        genreBox.getSelectionModel().clearSelection();
    }
}
