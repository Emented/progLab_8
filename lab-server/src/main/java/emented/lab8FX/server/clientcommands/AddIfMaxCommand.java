package emented.lab8FX.server.clientcommands;


import emented.lab8FX.common.util.Request;
import emented.lab8FX.common.util.Response;
import emented.lab8FX.server.util.CommandProcessor;
import emented.lab8FX.server.abstractions.AbstractClientCommand;

public class AddIfMaxCommand extends AbstractClientCommand {

    private final CommandProcessor commandProcessor;

    public AddIfMaxCommand(CommandProcessor commandProcessor) {
        super("add_if_max",
                0,
                "add a new item to the collection if its value exceeds the value of the largest item in this collection");
        this.commandProcessor = commandProcessor;
    }

    @Override
    public Response executeClientCommand(Request request) {
        return commandProcessor.addIfMax(request);
    }
}
