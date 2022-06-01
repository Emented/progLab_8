package emented.lab8FX.client.models;

import emented.lab8FX.client.controllers.RemoveAnyController;
import emented.lab8FX.client.exceptions.FieldsValidationException;
import emented.lab8FX.client.util.ClientSocketWorker;
import emented.lab8FX.client.util.Session;
import emented.lab8FX.client.util.validators.NumberValidator;
import emented.lab8FX.common.util.requests.CommandRequest;
import javafx.stage.Stage;

import java.util.List;
import java.util.Objects;

public class RemoveAnyModel extends AbstractModel {

    private final Session session;

    private final RemoveAnyController currentController;

    public RemoveAnyModel(ClientSocketWorker clientSocketWorker, Stage currentStage, Session session, RemoveAnyController currentController) {
        super(clientSocketWorker, currentStage);
        this.session = session;
        this.currentController = currentController;
    }

    public void processRemove(String number) throws FieldsValidationException {
        List<String> errorList = NumberValidator.validateNumber(number);
        if (errorList.stream().anyMatch(Objects::nonNull)) {
            throw new FieldsValidationException(errorList);
        }
        currentController.getMainModel().getThreadPoolExecutor().execute(currentController.getMainModel().generateTask(new CommandRequest("remove_any_by_number_of_participants",
                NumberValidator.getValidatedNumberOfParticipants(number),
                session.getUsername(),
                session.getPassword(),
                getClientInfo()), true));
    }
}
