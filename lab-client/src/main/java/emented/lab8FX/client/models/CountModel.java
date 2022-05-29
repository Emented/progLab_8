package emented.lab8FX.client.models;

import emented.lab8FX.client.controllers.CountController;
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

public class CountModel extends AbstractModel {

    private final Session session;

    private final CountController countController;

    public CountModel(ClientSocketWorker clientSocketWorker, Stage currentStage, Session session, CountController countController) {
        super(clientSocketWorker, currentStage);
        this.session = session;
        this.countController = countController;
    }

    public Alert processCount(String number) throws ExceptionWithAlert, FieldsValidationException {
        try {
            List<String> errorList = NumberValidator.validateNumber(number);
            if (errorList.stream().anyMatch(Objects::nonNull)) {
                throw new FieldsValidationException(errorList);
            }
            AbstractResponse response = getClientSocketWorker().proceedTransaction(new CommandRequest("count_less_than_number_of_participants",
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
