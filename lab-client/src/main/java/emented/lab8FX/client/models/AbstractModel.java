package emented.lab8FX.client.models;

import java.io.IOException;

import emented.lab8FX.client.controllers.AbstractController;
import emented.lab8FX.client.exceptions.ExceptionWithAlert;
import emented.lab8FX.client.util.ClientSocketWorker;
import emented.lab8FX.client.util.PathToViews;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Callback;

public abstract class AbstractModel {

    private final String clientInfo;
    private final ClientSocketWorker clientSocketWorker;
    private final Stage currentStage;

    public AbstractModel(ClientSocketWorker clientSocketWorker, Stage currentStage) {
        this.clientSocketWorker = clientSocketWorker;
        this.currentStage = currentStage;
        this.clientInfo = clientSocketWorker.getAddress() + ":" + clientSocketWorker.getPort();
    }

    public ClientSocketWorker getClientSocketWorker() {
        return this.clientSocketWorker;
    }

    public Stage getCurrentStage() {
        return currentStage;
    }

    public String getClientInfo() {
        return clientInfo;
    }
}
