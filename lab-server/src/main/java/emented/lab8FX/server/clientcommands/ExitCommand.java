package emented.lab8FX.server.clientcommands;

import emented.lab8FX.common.util.Request;
import emented.lab8FX.common.util.Response;
import emented.lab8FX.server.abstractions.AbstractClientCommand;

public class ExitCommand extends AbstractClientCommand {

    public ExitCommand() {
        super("exit",
                0,
                "shut down the client (all your changes will be lost)");
    }

    @Override
    public Response executeClientCommand(Request request) {
        return null;
    }
}
