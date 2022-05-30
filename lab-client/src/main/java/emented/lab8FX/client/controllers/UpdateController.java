package emented.lab8FX.client.controllers;

import emented.lab8FX.client.exceptions.ExceptionWithAlert;
import emented.lab8FX.client.exceptions.FieldsValidationException;
import emented.lab8FX.client.models.MainModel;
import emented.lab8FX.client.models.UpdateModel;
import emented.lab8FX.client.util.ClientSocketWorker;
import emented.lab8FX.client.util.Session;
import emented.lab8FX.common.entities.enums.MusicGenre;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UpdateController extends AbstractController implements Initializable {

    private final UpdateModel updateModel;

    private final MainModel mainModel;
    @FXML
    private TextField idField;
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

    public UpdateController(ClientSocketWorker clientSocketWorker, Session session, MainModel mainModel) {
        updateModel = new UpdateModel(clientSocketWorker, getCurrentStage(), session, this);
        this.mainModel = mainModel;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setResourceBundle(resources);
        genreBox.setItems(FXCollections.observableArrayList(Stream.of(MusicGenre.values()).collect(Collectors.toList())));
        addRegex(idField, xField, yField, numberField);
    }

    public void setFields(Long id, String name, Double x, Float y, Long number, MusicGenre genre, String description, String address) {
        idField.setText(id.toString());
        nameField.setText(name);
        xField.setText(x.toString());
        yField.setText(y.toString());
        numberField.setText(number.toString());
        genreBox.setValue(genre);
        descriptionField.setText(description);
        addressField.setText(address);
    }

    @FXML
    public void updateAction() {
        List<TextField> textFields = Arrays.asList(idField, nameField, xField, yField, numberField, descriptionField, addressField);
        removeFieldsColoring(textFields);
        try {
            Alert alert = updateModel.processUpdate(idField.getText(),
                    nameField.getText(),
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
    public void checkAction() {
        List<TextField> textFields = List.of(idField);
        removeFieldsColoring(textFields);
        try {
            Alert alert = updateModel.checkId(idField.getText());
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

    public void clearGenreAction() {
        genreBox.getSelectionModel().clearSelection();
    }
}
