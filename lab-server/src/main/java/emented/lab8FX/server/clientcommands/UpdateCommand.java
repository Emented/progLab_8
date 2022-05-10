package emented.lab8FX.server.clientcommands;

import emented.lab8FX.common.util.Response;
import emented.lab8FX.common.util.Request;
import emented.lab8FX.server.abstractions.AbstractClientCommand;
import emented.lab8FX.server.util.CommandProcessor;

public class UpdateCommand extends AbstractClientCommand {

    private final CommandProcessor commandProcessor;

    public UpdateCommand(CommandProcessor commandProcessor) {
        super("update", 1,
                "update the value of a collection item whose id is equal to the specified one",
                "id");
        this.commandProcessor = commandProcessor;
    }

    @Override
    public Response executeClientCommand(Request request) {
        return commandProcessor.updateById(request);
    }
}
