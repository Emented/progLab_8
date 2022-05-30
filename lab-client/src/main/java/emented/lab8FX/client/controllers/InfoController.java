package emented.lab8FX.client.controllers;

import emented.lab8FX.client.exceptions.ExceptionWithAlert;
import emented.lab8FX.client.models.MainModel;
import emented.lab8FX.client.util.PathToViews;
import emented.lab8FX.common.entities.MusicBand;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class InfoController extends AbstractController {


    private final MusicBand musicBand;
    private final MainModel mainModel;
    @FXML
    public Label bandLabel;

    public InfoController(MusicBand band, MainModel mainModel) {
        this.musicBand = band;
        this.mainModel = mainModel;
    }

    public void initialize() {
        bandLabel.setText(musicBand.toString());
    }

    @FXML
    public void updateAction() {
        try {
            UpdateController controller = showPopUpStage(PathToViews.UPDATE_VIEW,
                    param -> new UpdateController(mainModel.getClientSocketWorker(), mainModel.getSession(), mainModel),
                    "Update menu");
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
                    "Remove by id menu");
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
