package emented.lab8FX.server.clientcommands;


import emented.lab8FX.common.util.Request;
import emented.lab8FX.common.util.Response;
import emented.lab8FX.common.util.requests.CommandRequest;
import emented.lab8FX.common.util.responses.CommandResponse;
import emented.lab8FX.server.abstractions.AbstractClientCommand;
import emented.lab8FX.server.util.CommandProcessor;

public class CountLessThatNumberOfParticipantsCommand extends AbstractClientCommand {

    private final CommandProcessor commandProcessor;

    public CountLessThatNumberOfParticipantsCommand(CommandProcessor commandProcessor) {
        super("count_less_than_number_of_participants",
                1,
                "print the number of groups whose number of participants is less than the specified one",
                "number of participants");
        this.commandProcessor = commandProcessor;
    }

    @Override
    public CommandResponse executeClientCommand(CommandRequest request) {
        return commandProcessor.countLessThenNumberOfParticipants(request);
    }
}
