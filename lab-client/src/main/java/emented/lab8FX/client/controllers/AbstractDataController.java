package emented.lab8FX.client.controllers;

import emented.lab8FX.common.entities.MusicBand;
import javafx.fxml.Initializable;

import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

public abstract class AbstractDataController implements Initializable {

    private final MainController mainController;
    private ResourceBundle resourceBundle;

    public AbstractDataController(MainController mainController) {
        this.mainController = mainController;
    }

    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    public void setResourceBundle(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    public MainController getMainController() {
        return mainController;
    }

    public abstract void updateElements(List<MusicBand> elementsToAdd, List<MusicBand> elementsToRemove, List<MusicBand> elementsToUpdate, List<Long> usersIDs);

    public abstract void initializeElements(Set<MusicBand> musicBandSet, List<Long> usersIDs);
}
