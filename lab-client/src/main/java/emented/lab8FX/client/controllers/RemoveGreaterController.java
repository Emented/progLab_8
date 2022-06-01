package emented.lab8FX.client.controllers;

import emented.lab8FX.client.exceptions.FieldsValidationException;
import emented.lab8FX.client.models.MainModel;
import emented.lab8FX.client.models.RemoveGreaterModel;
import emented.lab8FX.client.util.ClientSocketWorker;
import emented.lab8FX.client.util.Session;
import emented.lab8FX.common.entities.enums.MusicGenre;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RemoveGreaterController extends AbstractController implements Initializable {

    private final MainModel mainModel;
    private final RemoveGreaterModel removeGreaterModel;
    @FXML
    private TextField nameField;
    @FXML
    private TextField xField;
    @FXML
    private TextField yField;
    @FXML
    private TextField numberField;
    @FXML
    private TextField descriptionField;
    @FXML
    private ChoiceBox<MusicGenre> genreBox;
    @FXML
    private TextField addressField;

    public RemoveGreaterController(ClientSocketWorker clientSocketWorker, Session session, MainModel mainModel) {
        this.removeGreaterModel = new RemoveGreaterModel(clientSocketWorker, getCurrentStage(), session, this);
        this.mainModel = mainModel;
    }

    public MainModel getMainModel() {
        return mainModel;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setResourceBundle(resources);
        genreBox.setItems(FXCollections.observableArrayList(Stream.of(MusicGenre.values()).collect(Collectors.toList())));
        addRegex(xField, yField, numberField);
    }

    public void setFields(String name, Double x, Float y, Long number, MusicGenre genre, String description, String address) {
        nameField.setText(name);
        xField.setText(x.toString());
        yField.setText(y.toString());
        numberField.setText(number.toString());
        genreBox.setValue(genre);
        descriptionField.setText(description);
        addressField.setText(address);
    }

    @FXML
    public void removeAction() {
        List<TextField> textFields = Arrays.asList(nameField, xField, yField, numberField, descriptionField, addressField);
        removeFieldsColoring(textFields);
        try {
            removeGreaterModel.processRemove(nameField.getText(),
                    xField.getText(),
                    yField.getText(),
                    numberField.getText(),
                    genreBox.getValue(),
                    descriptionField.getText(),
                    addressField.getText());
            getCurrentStage().close();
        } catch (FieldsValidationException e) {
            showFieldsErrors(e.getErrorList(), textFields);
        }
    }

    public void clearGenreAction() {
        genreBox.getSelectionModel().clearSelection();
    }
}
