package emented.lab8FX.server.clientcommands;

import emented.lab8FX.common.util.Response;
import emented.lab8FX.common.util.Request;
import emented.lab8FX.common.util.requests.CommandRequest;
import emented.lab8FX.common.util.responses.CommandResponse;
import emented.lab8FX.server.util.CommandProcessor;
import emented.lab8FX.server.abstractions.AbstractClientCommand;

public class ClearCommand extends AbstractClientCommand {

    private final CommandProcessor commandProcessor;

    public ClearCommand(CommandProcessor commandProcessor) {
        super("clear",
                0,
                "clear the collection");
        this.commandProcessor = commandProcessor;
    }

    @Override
    public CommandResponse executeClientCommand(CommandRequest request) {
        return commandProcessor.clear(request);
    }
}
