package emented.lab8FX.client.controllers;

import emented.lab8FX.client.exceptions.ExceptionWithAlert;
import emented.lab8FX.client.models.AbstractModel;
import emented.lab8FX.client.util.PathToViews;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;

public abstract class AbstractController {
    private Stage currentStage;

    public Stage getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(Stage currentStage) {
        this.currentStage = currentStage;
    }
    public <T extends AbstractController> void switchScene(PathToViews pathToViews,
                                                           Callback<Class<?>, Object> constructorCallback) throws ExceptionWithAlert {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource(pathToViews.getPath()));
            fxmlLoader.setControllerFactory(constructorCallback);
            Parent parent = fxmlLoader.load();
            Scene scene = new Scene(parent);
            T controller = fxmlLoader.getController();
            controller.setCurrentStage(getCurrentStage());
            getCurrentStage().setScene(scene);
            getCurrentStage().sizeToScene();
            getCurrentStage().centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
            throw new ExceptionWithAlert("Troubles during switching to next stage!");
        }
    }
}
