package emented.lab8FX.server.servercommands;


import emented.lab8FX.common.util.TextColoring;
import emented.lab8FX.server.ServerConfig;
import emented.lab8FX.server.abstractions.AbstractServerCommand;

public class ServerExitCommand extends AbstractServerCommand {

    public ServerExitCommand() {
        super("exit", "shut downs the server");
    }

    @Override
    public String executeServerCommand() {
        ServerConfig.toggleRun();
        return TextColoring.getGreenText("Server shutdown");
    }
}
