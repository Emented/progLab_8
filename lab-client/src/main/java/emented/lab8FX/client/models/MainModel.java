package emented.lab8FX.client.models;

import emented.lab8FX.client.util.ClientSocketWorker;
import emented.lab8FX.client.util.Session;
import javafx.stage.Stage;

public class MainModel extends AbstractModel {

    private Session session;

    public MainModel(ClientSocketWorker clientSocketWorker, Stage currentStage, Session session) {
        super(clientSocketWorker, currentStage);
        this.session = session;
    }
}
