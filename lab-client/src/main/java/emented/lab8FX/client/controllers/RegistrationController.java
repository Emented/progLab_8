package emented.lab8FX.client.controllers;

import emented.lab8FX.client.util.ClientSocketWorker;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegistrationController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField firstPasswordField;

    @FXML
    private PasswordField secondPasswordField;


    private final ClientSocketWorker clientSocketWorker;

    public RegistrationController(ClientSocketWorker clientSocketWorker) {
        this.clientSocketWorker = clientSocketWorker;
    }

    @FXML
    public void registerAction() {
        System.out.println("res");
    }

    @FXML
    public void loginAction() {
        System.out.println("log");
    }
}
