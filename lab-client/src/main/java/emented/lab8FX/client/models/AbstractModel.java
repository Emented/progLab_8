package emented.lab8FX.client.models;

import emented.lab8FX.client.util.ClientSocketWorker;
import emented.lab8FX.common.abstractions.AbstractResponse;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public abstract class AbstractModel {

    private final String clientInfo;
    private final ClientSocketWorker clientSocketWorker;
    private final Stage currentStage;

    public AbstractModel(ClientSocketWorker clientSocketWorker, Stage currentStage) {
        this.clientSocketWorker = clientSocketWorker;
        this.currentStage = currentStage;
        this.clientInfo = clientSocketWorker.getAddress() + ":" + clientSocketWorker.getPort();
    }

    public Alert getResponseInfo(AbstractResponse response) {
        Alert alert;
        if (response.isSuccess()) {
            alert = new Alert(Alert.AlertType.INFORMATION, response.toString());
        } else {
            alert = new Alert(Alert.AlertType.ERROR, response.toString());
        }
        alert.setHeaderText(null);
        return alert;
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
