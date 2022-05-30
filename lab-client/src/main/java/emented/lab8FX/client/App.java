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
import java.util.Locale;
import java.util.ResourceBundle;

public class App extends Application {

    private static Scene scene;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        ClientSocketWorker clientSocketWorker = new ClientSocketWorker();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(App.class.getResource(PathToViews.CONNECTION_VIEW.getPath()));
        loader.setResources(ResourceBundle.getBundle("localization.locale", new Locale("en")));
        loader.setControllerFactory(cont -> new ConnectionController(clientSocketWorker));
        Parent parent = loader.load();
        scene = new Scene(parent);
        ConnectionController connectionController = loader.getController();
        connectionController.setCurrentStage(primaryStage);
        primaryStage.setTitle(loader.getResources().getString("app.menu"));
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.show();
    }
}
