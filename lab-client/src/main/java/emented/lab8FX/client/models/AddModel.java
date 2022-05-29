package emented.lab8FX.client.models;

import emented.lab8FX.client.controllers.AddController;
import emented.lab8FX.client.controllers.MainController;
import emented.lab8FX.client.util.ClientSocketWorker;
import emented.lab8FX.client.util.Session;
import javafx.stage.Stage;

public class AddModel extends AbstractModel {

    private final Session session;

    private final AddController addController;

    public AddModel(ClientSocketWorker clientSocketWorker, Stage currentStage, Session session, AddController addController) {
        super(clientSocketWorker, currentStage);
        this.session = session;
        this.addController = addController;
    }
}
