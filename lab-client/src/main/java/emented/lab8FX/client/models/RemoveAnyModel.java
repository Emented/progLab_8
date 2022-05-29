package emented.lab8FX.client.models;

import emented.lab8FX.client.controllers.RemoveAnyController;
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

public class RemoveAnyModel extends AbstractModel {

    private final Session session;

    private final RemoveAnyController removeAnyController;

    public RemoveAnyModel(ClientSocketWorker clientSocketWorker, Stage currentStage, Session session, RemoveAnyController removeAnyController) {
        super(clientSocketWorker, currentStage);
        this.session = session;
        this.removeAnyController = removeAnyController;
    }

    public Alert processRemove(String number) throws FieldsValidationException, ExceptionWithAlert {
        try {
            List<String> errorList = NumberValidator.validateNumber(number);
            if (errorList.stream().anyMatch(Objects::nonNull)) {
                throw new FieldsValidationException(errorList);
            }
            AbstractResponse response = getClientSocketWorker().proceedTransaction(new CommandRequest("remove_any_by_number_of_participants",
                    NumberValidator.getValidatedNumberOfParticipants(number),
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
