package emented.lab8FX.client.controllers;

import emented.lab8FX.client.exceptions.ExceptionWithAlert;
import emented.lab8FX.client.exceptions.FieldsValidationException;
import emented.lab8FX.client.models.ConnectionModel;
import emented.lab8FX.client.util.ClientSocketWorker;
import emented.lab8FX.client.util.LanguagesEnum;
import emented.lab8FX.client.util.PathToViews;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConnectionController extends AbstractController implements Initializable {

    private final ConnectionModel connectionModel;
    @FXML
    private ChoiceBox<LanguagesEnum> languageBox;
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
        languageBox.setItems(FXCollections.observableArrayList(Stream.of(LanguagesEnum.values()).collect(Collectors.toList())));
        languageBox.setValue(connectionModel.getLanguage(getResourceBundle().getLocale().getLanguage()));
        languageBox.getSelectionModel().selectedItemProperty().addListener((m, oldValue, newValue) -> {
            try {
                setResourceBundle(ResourceBundle.getBundle("localization.locale", new Locale(newValue.getLanguageName())));
                switchScene(PathToViews.CONNECTION_VIEW,
                        param -> new ConnectionController(connectionModel.getClientSocketWorker()), getResourceBundle());
                languageBox.setValue(newValue);
            } catch (ExceptionWithAlert e) {
                e.showAlert();
            }
        });
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
