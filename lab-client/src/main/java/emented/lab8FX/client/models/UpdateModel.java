package emented.lab8FX.client.models;

import emented.lab8FX.client.controllers.UpdateController;
import emented.lab8FX.client.exceptions.FieldsValidationException;
import emented.lab8FX.client.util.BandGenerator;
import emented.lab8FX.client.util.ClientSocketWorker;
import emented.lab8FX.client.util.Session;
import emented.lab8FX.client.util.validators.BandValidator;
import emented.lab8FX.client.util.validators.NumberValidator;
import emented.lab8FX.common.entities.enums.MusicGenre;
import emented.lab8FX.common.util.requests.CheckIdRequest;
import emented.lab8FX.common.util.requests.CommandRequest;
import javafx.stage.Stage;

import java.util.List;
import java.util.Objects;

public class UpdateModel extends AbstractModel {

    private final Session session;

    private final UpdateController currentController;

    public UpdateModel(ClientSocketWorker clientSocketWorker, Stage currentStage, Session session, UpdateController currentController) {
        super(clientSocketWorker, currentStage);
        this.session = session;
        this.currentController = currentController;
    }

    public void processUpdate(String id, String name, String x, String y, String number, MusicGenre genre, String description, String address) throws FieldsValidationException {
        List<String> errorList = NumberValidator.validateId(id);
        errorList.addAll(BandValidator.validateBand(name, x, y, number));
        if (errorList.stream().anyMatch(Objects::nonNull)) {
            throw new FieldsValidationException(errorList);
        }
        BandGenerator generator = new BandGenerator(name, x, y, number, genre, description, address);
        currentController.getMainModel().getThreadPoolExecutor().execute(currentController.getMainModel().generateTask(new CommandRequest("update",
                NumberValidator.getValidatedId(id),
                generator.getMusicBand(),
                session.getUsername(),
                session.getPassword(),
                getClientInfo()), true));
    }

    public void checkId(String id) throws FieldsValidationException {
        List<String> errorList = NumberValidator.validateId(id);
        if (errorList.stream().anyMatch(Objects::nonNull)) {
            throw new FieldsValidationException(errorList);
        }
        currentController.getMainModel().getThreadPoolExecutor().execute(currentController.getMainModel().generateTask(new CheckIdRequest(getClientInfo(),
                NumberValidator.getValidatedId(id),
                session.getUsername(),
                session.getPassword()), false));
    }
}
