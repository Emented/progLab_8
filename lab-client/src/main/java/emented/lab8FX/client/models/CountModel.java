package emented.lab8FX.client.models;

import emented.lab8FX.client.controllers.CountController;
import emented.lab8FX.client.exceptions.FieldsValidationException;
import emented.lab8FX.client.util.ClientSocketWorker;
import emented.lab8FX.client.util.Session;
import emented.lab8FX.client.util.validators.NumberValidator;
import emented.lab8FX.common.util.requests.CommandRequest;
import javafx.stage.Stage;

import java.util.List;
import java.util.Objects;

public class CountModel extends AbstractModel {

    private final Session session;

    private final CountController currentController;

    public CountModel(ClientSocketWorker clientSocketWorker, Stage currentStage, Session session, CountController currentController) {
        super(clientSocketWorker, currentStage);
        this.session = session;
        this.currentController = currentController;
    }

    public void processCount(String number) throws FieldsValidationException {
        List<String> errorList = NumberValidator.validateNumber(number);
        if (errorList.stream().anyMatch(Objects::nonNull)) {
            throw new FieldsValidationException(errorList);
        }
        currentController.getMainModel().getThreadPoolExecutor().execute(currentController.getMainModel().generateTask(new CommandRequest("count_less_than_number_of_participants",
                NumberValidator.getValidatedNumberOfParticipants(number),
                session.getUsername(),
                session.getPassword(),
                getClientInfo()), false));
    }
}
