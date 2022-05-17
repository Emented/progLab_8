package emented.lab8FX.server.clientcommands;

import emented.lab8FX.common.util.Response;
import emented.lab8FX.common.util.Request;
import emented.lab8FX.common.util.requests.CommandRequest;
import emented.lab8FX.common.util.responses.CommandResponse;
import emented.lab8FX.server.util.CommandProcessor;
import emented.lab8FX.server.abstractions.AbstractClientCommand;

public class AddCommand extends AbstractClientCommand {

    private final CommandProcessor commandProcessor;

    public AddCommand(CommandProcessor commandProcessor) {
        super("add",
                0,
                "add a new item to the collection");
        this.commandProcessor = commandProcessor;
    }

    @Override
    public CommandResponse executeClientCommand(CommandRequest request) {
        return commandProcessor.add(request);
    }
}
