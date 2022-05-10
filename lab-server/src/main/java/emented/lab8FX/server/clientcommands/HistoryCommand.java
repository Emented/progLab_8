package emented.lab8FX.server.clientcommands;


import emented.lab8FX.common.util.Request;
import emented.lab8FX.common.util.Response;
import emented.lab8FX.server.abstractions.AbstractClientCommand;
import emented.lab8FX.server.util.CommandProcessor;

import java.util.ArrayDeque;

public class HistoryCommand extends AbstractClientCommand {

    private final ArrayDeque<String> queueOfCommands;
    private final CommandProcessor commandProcessor;


    public HistoryCommand(ArrayDeque<String> queueOfCommands, CommandProcessor commandProcessor) {
        super("history",
                0,
                "output the last 9 commands");
        this.queueOfCommands = queueOfCommands;
        this.commandProcessor = commandProcessor;
    }

    @Override
    public Response executeClientCommand(Request request) {
        return commandProcessor.history(request, queueOfCommands);
    }
}
