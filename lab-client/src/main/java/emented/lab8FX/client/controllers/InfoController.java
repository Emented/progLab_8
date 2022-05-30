package emented.lab8FX.client.controllers;

import emented.lab8FX.client.exceptions.ExceptionWithAlert;
import emented.lab8FX.client.models.MainModel;
import emented.lab8FX.client.util.PathToViews;
import emented.lab8FX.common.entities.MusicBand;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class InfoController extends AbstractController implements Initializable {


    private final MusicBand musicBand;
    private final MainModel mainModel;
    @FXML
    private Label bandLabel;

    public InfoController(MusicBand band, MainModel mainModel) {
        this.musicBand = band;
        this.mainModel = mainModel;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setResourceBundle(resources);
        bandLabel.setText(musicBand.toString());
    }

    @FXML
    public void updateAction() {
        try {
            UpdateController controller = showPopUpStage(PathToViews.UPDATE_VIEW,
                    param -> new UpdateController(mainModel.getClientSocketWorker(), mainModel.getSession(), mainModel),
                    getResourceBundle().getString("update_menu.title"),
                    getResourceBundle());
            controller.setFields(musicBand.getId(),
                    musicBand.getName(),
                    musicBand.getCoordinates().getX(),
                    musicBand.getCoordinates().getY(),
                    musicBand.getNumberOfParticipants(),
                    musicBand.getGenre(),
                    musicBand.getDescription(),
                    (musicBand.getStudio() == null) ? null : musicBand.getStudio().getAddress());
            getCurrentStage().close();
        } catch (ExceptionWithAlert e) {
            e.showAlert();
        }
    }

    @FXML
    public void removeAction() {
        try {
            RemoveByIdController controller = showPopUpStage(PathToViews.REMOVE_BY_ID_VIEW,
                    param -> new RemoveByIdController(mainModel.getClientSocketWorker(), mainModel.getSession(), mainModel),
                    getResourceBundle().getString("remove_by_id.title"),
                    getResourceBundle());
            controller.setField(musicBand.getId());
            getCurrentStage().close();
        } catch (ExceptionWithAlert e) {
            e.showAlert();
        }
    }

    @FXML
    public void closeAction() {
        getCurrentStage().close();
    }
}
