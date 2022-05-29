package emented.lab8FX.client.controllers;

import emented.lab8FX.client.util.ClientSocketWorker;
import emented.lab8FX.client.util.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class RemoveByIdController extends AbstractController {

    @FXML public TextField idField;

    public RemoveByIdController(ClientSocketWorker clientSocketWorker, Session session) {
    }

    @FXML public void removeAction(ActionEvent actionEvent) {
    }
}
