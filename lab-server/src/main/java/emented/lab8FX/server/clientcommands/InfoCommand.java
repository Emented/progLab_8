package emented.lab8FX.server.clientcommands;

import emented.lab8FX.common.util.Request;
import emented.lab8FX.common.util.Response;
import emented.lab8FX.server.abstractions.AbstractClientCommand;
import emented.lab8FX.server.util.CommandProcessor;

public class InfoCommand extends AbstractClientCommand {

    private final CommandProcessor commandProcessor;
    public InfoCommand(CommandProcessor commandProcessor) {
        super("info",
                0,
                "display information about the collection");
        this.commandProcessor = commandProcessor;
    }

    @Override
    public Response executeClientCommand(Request request) {
        return commandProcessor.info(request);
    }
}
