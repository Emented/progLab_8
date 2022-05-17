package emented.lab8FX.server.clientcommands;

import emented.lab8FX.common.util.Request;
import emented.lab8FX.common.util.Response;
import emented.lab8FX.common.util.requests.CommandRequest;
import emented.lab8FX.common.util.responses.CommandResponse;
import emented.lab8FX.server.abstractions.AbstractClientCommand;
import emented.lab8FX.server.util.CommandProcessor;

public class RemoveGreaterCommand extends AbstractClientCommand {

    private final CommandProcessor commandProcessor;

    public RemoveGreaterCommand(CommandProcessor commandProcessor) {
        super("remove_greater",
                0,
                "remove all items from the collection that exceed the specified");
        this.commandProcessor = commandProcessor;
    }

    @Override
    public CommandResponse executeClientCommand(CommandRequest request) {
        return commandProcessor.removeGreater(request);
    }
}
