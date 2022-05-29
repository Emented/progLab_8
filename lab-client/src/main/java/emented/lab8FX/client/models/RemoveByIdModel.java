package emented.lab8FX.client.models;

import emented.lab8FX.client.controllers.RemoveByIdController;
import emented.lab8FX.client.exceptions.ExceptionWithAlert;
import emented.lab8FX.client.exceptions.FieldsValidationException;
import emented.lab8FX.client.util.ClientSocketWorker;
import emented.lab8FX.client.util.Session;
import emented.lab8FX.client.util.validators.NumberValidator;
import emented.lab8FX.common.abstractions.AbstractResponse;
import emented.lab8FX.common.util.requests.CommandRequest;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class RemoveByIdModel extends AbstractModel {

    private final Session session;

    private final RemoveByIdController removeByIdController;

    public RemoveByIdModel(ClientSocketWorker clientSocketWorker, Stage currentStage, Session session, RemoveByIdController removeByIdController) {
        super(clientSocketWorker, currentStage);
        this.session = session;
        this.removeByIdController = removeByIdController;
    }

    public Alert processRemove(String id) throws FieldsValidationException, ExceptionWithAlert {
        try {
            List<String> errorList = NumberValidator.validateId(id);
            if (errorList.stream().anyMatch(Objects::nonNull)) {
                throw new FieldsValidationException(errorList);
            }
            AbstractResponse response = getClientSocketWorker().proceedTransaction(new CommandRequest("remove_by_id",
                    NumberValidator.getValidatedId(id),
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