package emented.lab8FX.server.clientcommands;

import emented.lab8FX.common.util.Request;
import emented.lab8FX.common.util.Response;
import emented.lab8FX.server.abstractions.AbstractClientCommand;
import emented.lab8FX.server.util.CommandProcessor;

public class ShowCommand extends AbstractClientCommand {

    private final CommandProcessor commandProcessor;

    public ShowCommand(CommandProcessor commandProcessor) {
        super("show",
                0,
                "display all the elements of the collection and information about them");
        this.commandProcessor = commandProcessor;
    }

    @Override
    public Response executeClientCommand(Request request) {
        return commandProcessor.show(request);
    }
}
