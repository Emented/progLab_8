package emented.lab8FX.client.controllers;

import emented.lab8FX.client.exceptions.ExceptionWithAlert;
import emented.lab8FX.client.exceptions.FieldsValidationException;
import emented.lab8FX.client.models.RegistrationModel;
import emented.lab8FX.client.util.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RegistrationController extends AbstractController implements Initializable {
    private final RegistrationModel registrationModel;
    @FXML
    private ChoiceBox<LanguagesEnum> languageBox;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField firstPasswordField;
    @FXML
    private PasswordField secondPasswordField;

    public RegistrationController(ClientSocketWorker clientSocketWorker) {
        this.registrationModel = new RegistrationModel(clientSocketWorker, getCurrentStage(), this);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setResourceBundle(resources);
        languageBox.setItems(FXCollections.observableArrayList(Stream.of(LanguagesEnum.values()).collect(Collectors.toList())));
        languageBox.setValue(registrationModel.getLanguage(getResourceBundle().getLocale().getLanguage()));
        languageBox.getSelectionModel().selectedItemProperty().addListener((m, oldValue, newValue) -> {
            try {
                setResourceBundle(ResourceBundle.getBundle("localization.locale", new Locale(newValue.getLanguageName())));
                switchScene(PathToViews.REGISTRATION_VIEW,
                        param -> new RegistrationController(registrationModel.getClientSocketWorker()), getResourceBundle());
                languageBox.setValue(newValue);
            } catch (ExceptionWithAlert e) {
                e.showAlert();
            }
        });
    }

    @FXML
    public void loginAction() {
        try {
            switchScene(PathToViews.LOGIN_VIEW, param -> new LoginController(registrationModel.getClientSocketWorker()), getResourceBundle());
        } catch (ExceptionWithAlert e) {
            e.showAlert();
        }
    }

    @FXML
    public void registerAction() {
        List<TextField> textFields = Arrays.asList(usernameField, firstPasswordField, secondPasswordField);
        removeFieldsColoring(textFields);
        try {
            Session session = registrationModel.processRegistration(usernameField.getText(),
                    firstPasswordField.getText(),
                    secondPasswordField.getText());
            switchScene(PathToViews.MAIN_VIEW, param -> new MainController(registrationModel.getClientSocketWorker(), session, PathToVisuals.TABLE_VIEW), getResourceBundle());
        } catch (ExceptionWithAlert e) {
            if (e.isFatal()) {
                e.showAlert();
                registrationModel.getClientSocketWorker().closeSocket();
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
