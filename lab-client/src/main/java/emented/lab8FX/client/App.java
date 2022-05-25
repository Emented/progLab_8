package emented.lab8FX.client;

import emented.lab8FX.client.controllers.ConnectionController;
import emented.lab8FX.client.models.ConnectionModel;
import emented.lab8FX.client.util.PathToViews;
import emented.lab8FX.client.util.ClientSocketWorker;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private static Scene scene;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        ClientSocketWorker clientSocketWorker = new ClientSocketWorker();
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(PathToViews.CONNECTION_VIEW.getPath()));
        Parent parent = fxmlLoader.load();
        scene = new Scene(parent);
        ConnectionController connectionController = fxmlLoader.getController();
        connectionController.setModel(new ConnectionModel(clientSocketWorker, primaryStage));
        connectionController.initializeController();
        primaryStage.setTitle("MusicBands Application");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.show();
    }
}
