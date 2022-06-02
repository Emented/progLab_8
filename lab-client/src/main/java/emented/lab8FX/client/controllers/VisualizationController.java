package emented.lab8FX.client.controllers;

import emented.lab8FX.common.entities.MusicBand;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

public class VisualizationController extends AbstractDataController implements Initializable {

    private final HashMap<MusicBand, Canvas> visualBands = new HashMap<>();
    @FXML
    public Pane bandsPane;

    public VisualizationController(MainController mainController) {
        super(mainController);
    }

    @Override
    public void updateElements(List<MusicBand> elementsToAdd, List<MusicBand> elementsToRemove, List<MusicBand> elementsToUpdate, List<Long> usersIDs) {
        for (MusicBand m : elementsToAdd) {
            addToVisual(m, !usersIDs.contains(m.getId()));
        }
        for (MusicBand m : elementsToRemove) {
            removeFromVisual(m);
        }
        List<Long> idsToUpdate = elementsToUpdate.stream().map(MusicBand::getId).toList();
        for (Long id : idsToUpdate) {
            MusicBand m = elementsToUpdate.stream().filter(musicBand -> musicBand.getId().equals(id)).toList().get(0);
            MusicBand n = visualBands.keySet().stream().filter(musicBand -> musicBand.getId().equals(id)).toList().get(0);
            removeFromVisual(n);
            addToVisual(m, !usersIDs.contains(m.getId()));
        }
    }

    @Override
    public void initializeElements(Set<MusicBand> musicBandSet, List<Long> usersIDs) {
        for (MusicBand m : musicBandSet) {
            addToVisual(m, !usersIDs.contains(m.getId()));
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setResourceBundle(resources);
    }

    public void removeFromVisual(MusicBand musicBand) {
        Canvas canvas = visualBands.get(musicBand);
        bandsPane.getChildren().remove(canvas);
    }


    public void addToVisual(MusicBand musicBand, boolean alien) {
        Color color;
        if (alien) {
            color = Color.web("4143C4FF");
        } else {
            color = Color.web("5CD20EFF");
        }
        Canvas canvas = getMainController().getMainModel().generateBandCanvas(color, musicBand);
        visualBands.put(musicBand, canvas);
        FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.millis(1500));
        fade.setFromValue(0);
        fade.setToValue(10);
        fade.setNode(canvas);
        fade.play();
        bandsPane.getChildren().add(canvas);
    }
}
