package emented.lab8FX.server.clientcommands;
import emented.lab8FX.common.util.Request;
import emented.lab8FX.common.util.Response;
import emented.lab8FX.server.abstractions.AbstractClientCommand;
import emented.lab8FX.server.util.CommandProcessor;

public class RemoveAnyByNumberOfParticipantsCommand extends AbstractClientCommand {

    private final CommandProcessor commandProcessor;

    public RemoveAnyByNumberOfParticipantsCommand(CommandProcessor commandProcessor) {
        super("remove_any_by_number_of_participants",
                1,
                "delete a group with a specified number of members from the collection",
                "number of participants");
        this.commandProcessor = commandProcessor;
    }

    @Override
    public Response executeClientCommand(Request request) {
        return commandProcessor.removeAnyByNumberOfParticipants(request);
    }
}
