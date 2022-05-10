package emented.lab8FX.server.clientcommands;


import emented.lab8FX.common.util.Request;
import emented.lab8FX.common.util.Response;
import emented.lab8FX.server.abstractions.AbstractClientCommand;

public class ExecuteScriptCommand extends AbstractClientCommand {

    public ExecuteScriptCommand() {
        super("execute_script",
                1,
                "read and execute the script from the specified file",
                "file name");
    }

    @Override
    public Response executeClientCommand(Request request) {
        return null;
    }
}
