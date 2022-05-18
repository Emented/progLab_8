package emented.lab8FX.client;

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
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/fxml/connection.fxml"));
        Parent parent = fxmlLoader.load();
        scene = new Scene(parent);
        primaryStage.setMinHeight(315);
        primaryStage.setMinWidth(450);
        primaryStage.setTitle("Connection menu");
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();
    }
}
