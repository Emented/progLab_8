package emented.lab8FX.client.models;

import emented.lab8FX.client.controllers.RemoveByIdController;
import emented.lab8FX.client.exceptions.FieldsValidationException;
import emented.lab8FX.client.util.ClientSocketWorker;
import emented.lab8FX.client.util.Session;
import emented.lab8FX.client.util.validators.NumberValidator;
import emented.lab8FX.common.util.requests.CommandRequest;
import javafx.stage.Stage;

import java.util.List;
import java.util.Objects;

public class RemoveByIdModel extends AbstractModel {

    private final Session session;

    private final RemoveByIdController currentController;

    public RemoveByIdModel(ClientSocketWorker clientSocketWorker, Stage currentStage, Session session, RemoveByIdController currentController) {
        super(clientSocketWorker, currentStage);
        this.session = session;
        this.currentController = currentController;
    }

    public void processRemove(String id) throws FieldsValidationException {
        List<String> errorList = NumberValidator.validateId(id);
        if (errorList.stream().anyMatch(Objects::nonNull)) {
            throw new FieldsValidationException(errorList);
        }
        currentController.getMainModel().getThreadPoolExecutor().execute(currentController.getMainModel().generateTask(new CommandRequest("remove_by_id",
                NumberValidator.getValidatedId(id),
                session.getUsername(),
                session.getPassword(),
                getClientInfo()), true));
    }
}
