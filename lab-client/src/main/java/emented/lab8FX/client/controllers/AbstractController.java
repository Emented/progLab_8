package emented.lab8FX.client.controllers;

import emented.lab8FX.client.exceptions.ExceptionWithAlert;
import emented.lab8FX.client.util.PathToViews;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;

public abstract class AbstractController {
    private Stage currentStage;

    public Stage getCurrentStage() {
        return currentStage;
    }

    private ResourceBundle resourceBundle;

    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    public void setResourceBundle(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    public void setCurrentStage(Stage currentStage) {
        this.currentStage = currentStage;
    }

    public <T extends AbstractController> void switchScene(PathToViews pathToViews,
                                                           Callback<Class<?>, Object> constructorCallback,
                                                           ResourceBundle resourceBundle) throws ExceptionWithAlert {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource(pathToViews.getPath()));
            fxmlLoader.setResources(resourceBundle);
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
            throw new ExceptionWithAlert(getResourceBundle().getString("switch_exception.scene"));
        }
    }

    public <T extends AbstractController> T showPopUpStage(PathToViews pathToViews,
                                                           Callback<Class<?>, Object> constructorCallback,
                                                           String name,
                                                           ResourceBundle resourceBundle) throws ExceptionWithAlert {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(pathToViews.getPath()));
            loader.setResources(resourceBundle);
            loader.setControllerFactory(constructorCallback);
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            T controller = loader.getController();
            controller.setCurrentStage(stage);
            stage.setTitle(name);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.sizeToScene();
            stage.show();
            return controller;
        } catch (IOException e) {
            e.printStackTrace();
            throw new ExceptionWithAlert(getResourceBundle().getString("switch_exception.pop_up"));
        }
    }

    public void showFieldsErrors(List<String> errorList, List<TextField> fieldList) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < errorList.size(); i++) {
            String error = errorList.get(i);
            if (error != null) {
                res.append(getResourceBundle().getString(error)).append("\n");
                fieldList.get(i).setBackground(new Background(new BackgroundFill(Color.web("#F8CECC"), null, null)));
                fieldList.get(i).clear();
            }
        }
        showErrorAlert(res.toString());
    }

    public void addRegex(TextField... textFields) {
        for (TextField t : textFields) {
            t.addEventFilter(KeyEvent.KEY_TYPED, keyEvent -> {
                if (!"9876543210-.,".contains(keyEvent.getCharacter())) {
                    keyEvent.consume();
                }
            });
        }
    }

    public void clearFields(List<TextField> fieldList) {
        for (TextField t : fieldList) {
            t.clear();
        }
    }

    public void removeFieldsColoring(List<TextField> fieldList) {
        for (TextField t : fieldList) {
            t.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        }
    }

    public void showErrorAlert(String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR, text);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    public void showInfoAlert(String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, text);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}
