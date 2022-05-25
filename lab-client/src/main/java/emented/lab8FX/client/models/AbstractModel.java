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

public abstract class AbstractModel {

    private final ClientSocketWorker clientSocketWorker;

    private final Stage currentStage;


    public AbstractModel(ClientSocketWorker clientSocketWorker, Stage currentStage) {
        this.clientSocketWorker = clientSocketWorker;
        this.currentStage = currentStage;
    }

    public ClientSocketWorker getClientSocketWorker() {
        return this.clientSocketWorker;
    }

    public Stage getCurrentStage() {
        return currentStage;
    }

    public <T extends AbstractController, V extends AbstractModel> void switchScene(PathToViews pathToViews, V model) throws ExceptionWithAlert {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource(pathToViews.getPath()));
            Parent parent = fxmlLoader.load();
            Scene scene = new Scene(parent);
            T controller = fxmlLoader.getController();
            controller.setModel(model);
            controller.initializeController();
            getCurrentStage().setScene(scene);
            getCurrentStage().sizeToScene();
        } catch (IOException e) {
            throw new ExceptionWithAlert("Troubles during switching to next stage!");
        }
    }
}
