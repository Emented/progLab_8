package emented.lab8FX.client.controllers;

import emented.lab8FX.client.models.AddModel;
import emented.lab8FX.client.util.ClientSocketWorker;
import emented.lab8FX.client.util.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class AddController extends AbstractController {

    private final AddModel addModel;
    @FXML public TextField nameField;
    @FXML public TextField xField;
    @FXML public TextField yField;
    @FXML public TextField numberField;
    @FXML public ChoiceBox genreBox;
    @FXML public TextField descriptionField;
    @FXML public TextField addressField;


    public AddController(ClientSocketWorker clientSocketWorker, Session session) {
        addModel = new AddModel(clientSocketWorker, getCurrentStage(), session, this);
    }


    @FXML public void addAction(ActionEvent actionEvent) {
    }

    @FXML public void addIfMaxAction(ActionEvent actionEvent) {
    }
}
