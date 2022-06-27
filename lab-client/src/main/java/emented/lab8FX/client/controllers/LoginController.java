package emented.lab8FX.client.controllers;

import emented.lab8FX.client.exceptions.ExceptionWithAlert;
import emented.lab8FX.client.exceptions.FieldsValidationException;
import emented.lab8FX.client.models.LoginModel;
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

public class LoginController extends AbstractController implements Initializable {
    private final LoginModel loginModel;
    @FXML
    private ChoiceBox<LanguagesEnum> languageBox;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    public LoginController(ClientSocketWorker clientSocketWorker) {
        this.loginModel = new LoginModel(clientSocketWorker, getCurrentStage(), this);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setResourceBundle(resources);
        languageBox.setItems(FXCollections.observableArrayList(Stream.of(LanguagesEnum.values()).collect(Collectors.toList())));
        languageBox.setValue(loginModel.getLanguage(getResourceBundle().getLocale().getLanguage()));
        languageBox.getSelectionModel().selectedItemProperty().addListener((m, oldValue, newValue) -> {
            try {
                setResourceBundle(ResourceBundle.getBundle("localization.locale", new Locale(newValue.getLanguageName())));
                switchScene(PathToViews.LOGIN_VIEW,
                        param -> new LoginController(loginModel.getClientSocketWorker()), getResourceBundle());
                languageBox.setValue(newValue);
            } catch (ExceptionWithAlert e) {
                e.showAlert();
            }
        });
    }

    @FXML
    public void loginAction() {
        List<TextField> textFields = Arrays.asList(usernameField, passwordField);
        removeFieldsColoring(textFields);
        try {
            Session session = loginModel.processLogin(usernameField.getText(),
                    passwordField.getText());
            switchScene(PathToViews.MAIN_VIEW, param -> new MainController(loginModel.getClientSocketWorker(), session, PathToVisuals.TABLE_VIEW), getResourceBundle());
        } catch (ExceptionWithAlert e) {
            if (e.isFatal()) {
                e.showAlert();
                loginModel.getClientSocketWorker().closeSocket();
                Platform.exit();
            } else {
                e.showAlert();
                clearFields(textFields);
            }
        } catch (FieldsValidationException e) {
            showFieldsErrors(e.getErrorList(), textFields);
        }
    }

    @FXML
    public void registerAction() {
        try {
            switchScene(PathToViews.REGISTRATION_VIEW, param -> new RegistrationController(loginModel.getClientSocketWorker()), getResourceBundle());
        } catch (ExceptionWithAlert e) {
            e.showAlert();
        }
    }
}
