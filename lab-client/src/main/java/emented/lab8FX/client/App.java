package emented.lab8FX.client;

import emented.lab8FX.client.controllers.ConnectionController;
import emented.lab8FX.client.util.ClientSocketWorker;
import emented.lab8FX.client.util.PathToViews;
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
        fxmlLoader.setControllerFactory(cont -> new ConnectionController(clientSocketWorker));
        Parent parent = fxmlLoader.load();
        scene = new Scene(parent);
        ConnectionController connectionController = fxmlLoader.getController();
        connectionController.setCurrentStage(primaryStage);
        primaryStage.setTitle("MusicBands Application");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.show();
    }
}
