package emented.lab8FX.server.clientcommands;

import emented.lab8FX.common.util.Request;
import emented.lab8FX.common.util.Response;
import emented.lab8FX.server.abstractions.AbstractClientCommand;
import emented.lab8FX.server.util.CommandProcessor;

import java.util.HashMap;

public class HelpCommand extends AbstractClientCommand {

    private final CommandProcessor commandProcessor;
    private final HashMap<String, AbstractClientCommand> availableCommands;

    public HelpCommand(HashMap<String, AbstractClientCommand> availableCommands, CommandProcessor commandProcessor) {
        super("help",
                0,
                "show list available commands");
        this.availableCommands = availableCommands;
        this.commandProcessor = commandProcessor;
    }

    @Override
    public Response executeClientCommand(Request request) {
        return commandProcessor.help(request, availableCommands);
    }
}
