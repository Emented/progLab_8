package emented.lab8FX.client.models;

import emented.lab8FX.client.controllers.UpdateController;
import emented.lab8FX.client.exceptions.ExceptionWithAlert;
import emented.lab8FX.client.exceptions.FieldsValidationException;
import emented.lab8FX.client.util.BandGenerator;
import emented.lab8FX.client.util.ClientSocketWorker;
import emented.lab8FX.client.util.Session;
import emented.lab8FX.client.util.validators.BandValidator;
import emented.lab8FX.client.util.validators.NumberValidator;
import emented.lab8FX.common.abstractions.AbstractResponse;
import emented.lab8FX.common.entities.enums.MusicGenre;
import emented.lab8FX.common.util.requests.CheckIdRequest;
import emented.lab8FX.common.util.requests.CommandRequest;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
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

    public Alert processUpdate(String id, String name, String x, String y, String number, MusicGenre genre, String description, String address) throws FieldsValidationException, ExceptionWithAlert {
        try {
            List<String> errorList = NumberValidator.validateId(id);
            errorList.addAll(BandValidator.validateBand(name, x, y, number));
            if (errorList.stream().anyMatch(Objects::nonNull)) {
                throw new FieldsValidationException(errorList);
            }
            BandGenerator generator = new BandGenerator(name, x, y, number, genre, description, address);
            AbstractResponse response = getClientSocketWorker().proceedTransaction(new CommandRequest("update",
                    NumberValidator.getValidatedId(id),
                    generator.getMusicBand(),
                    session.getUsername(),
                    session.getPassword(),
                    getClientInfo()));
            return getResponseInfo(response);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ExceptionWithAlert(currentController.getResourceBundle().getString("connection_exception.connection"));
        } catch (ClassNotFoundException e) {
            throw new ExceptionWithAlert(currentController.getResourceBundle().getString("connection_exception.response"));
        }
    }

    public Alert checkId(String id) throws ExceptionWithAlert, FieldsValidationException {
        try {
            List<String> errorList = NumberValidator.validateId(id);
            if (errorList.stream().anyMatch(Objects::nonNull)) {
                throw new FieldsValidationException(errorList);
            }
            AbstractResponse response = getClientSocketWorker().proceedTransaction(new CheckIdRequest(getClientInfo(),
                    NumberValidator.getValidatedId(id),
                    session.getUsername(),
                    session.getPassword()));
            return getResponseInfo(response);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ExceptionWithAlert(currentController.getResourceBundle().getString("connection_exception.connection"));
        } catch (ClassNotFoundException e) {
            throw new ExceptionWithAlert(currentController.getResourceBundle().getString("connection_exception.response"));
        }
    }
}
