package emented.lab8FX.client.models;

import emented.lab8FX.client.controllers.RemoveGreaterController;
import emented.lab8FX.client.exceptions.ExceptionWithAlert;
import emented.lab8FX.client.exceptions.FieldsValidationException;
import emented.lab8FX.client.util.BandGenerator;
import emented.lab8FX.client.util.ClientSocketWorker;
import emented.lab8FX.client.util.Session;
import emented.lab8FX.client.util.validators.BandValidator;
import emented.lab8FX.common.abstractions.AbstractResponse;
import emented.lab8FX.common.entities.enums.MusicGenre;
import emented.lab8FX.common.util.requests.CommandRequest;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class RemoveGreaterModel extends AbstractModel {

    private final Session session;

    private final RemoveGreaterController removeGreaterController;

    public RemoveGreaterModel(ClientSocketWorker clientSocketWorker, Stage currentStage, Session session, RemoveGreaterController removeGreaterController) {
        super(clientSocketWorker, currentStage);
        this.session = session;
        this.removeGreaterController = removeGreaterController;
    }

    public Alert processRemove(String name, String x, String y, String number, MusicGenre genre, String description, String address) throws FieldsValidationException, ExceptionWithAlert {
        try {
            List<String> errorList = BandValidator.validateBand(name, x, y, number);
            if (errorList.stream().anyMatch(Objects::nonNull)) {
                throw new FieldsValidationException(errorList);
            }
            BandGenerator generator = new BandGenerator(name, x, y, number, genre, description, address);
            AbstractResponse response = getClientSocketWorker().proceedTransaction(new CommandRequest("remove_greater",
                    generator.getMusicBand(),
                    session.getUsername(),
                    session.getPassword(),
                    getClientInfo()));
            return getResponseInfo(response);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ExceptionWithAlert("Some troubles with connection!");
        } catch (ClassNotFoundException e) {
            throw new ExceptionWithAlert("Response came damaged!");
        }
    }


}
