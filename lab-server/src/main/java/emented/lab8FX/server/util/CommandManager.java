package emented.lab8FX.server.util;

import emented.lab8FX.common.util.Request;
import emented.lab8FX.common.util.Response;
import emented.lab8FX.common.util.TextColoring;
import emented.lab8FX.common.util.requests.CommandRequest;
import emented.lab8FX.common.util.responses.CommandResponse;
import emented.lab8FX.server.ServerConfig;
import emented.lab8FX.server.abstractions.AbstractClientCommand;
import emented.lab8FX.server.abstractions.AbstractServerCommand;
import emented.lab8FX.server.clientcommands.AddCommand;
import emented.lab8FX.server.clientcommands.AddIfMaxCommand;
import emented.lab8FX.server.clientcommands.ClearCommand;
import emented.lab8FX.server.clientcommands.CountLessThatNumberOfParticipantsCommand;
import emented.lab8FX.server.clientcommands.HistoryCommand;
import emented.lab8FX.server.clientcommands.InfoCommand;
import emented.lab8FX.server.clientcommands.MinByStudioCommand;
import emented.lab8FX.server.clientcommands.RemoveAnyByNumberOfParticipantsCommand;
import emented.lab8FX.server.clientcommands.RemoveByIdCommand;
import emented.lab8FX.server.clientcommands.RemoveGreaterCommand;
import emented.lab8FX.server.clientcommands.ShowCommand;
import emented.lab8FX.server.clientcommands.UpdateCommand;
import emented.lab8FX.server.servercommands.ServerExitCommand;
import emented.lab8FX.server.servercommands.ServerHelpCommand;
import emented.lab8FX.server.servercommands.ServerHistoryCommand;

import java.time.format.DateTimeFormatter;

public class CommandManager {

    private final CommandProcessor commandProcessor;

    public CommandManager(CommandProcessor commandProcessor) {
        this.commandProcessor = commandProcessor;
        setCommands();
    }

    private void setCommands() {
        AbstractClientCommand infoCommand = new InfoCommand(commandProcessor);
        AbstractClientCommand showCommand = new ShowCommand(commandProcessor);
        AbstractClientCommand addCommand = new AddCommand(commandProcessor);
        AbstractClientCommand updateCommand = new UpdateCommand(commandProcessor);
        AbstractClientCommand removeByIdCommand = new RemoveByIdCommand(commandProcessor);
        AbstractClientCommand clearCommand = new ClearCommand(commandProcessor);
        AbstractClientCommand addIfMaxCommand = new AddIfMaxCommand(commandProcessor);
        AbstractClientCommand removeGreaterCommand = new RemoveGreaterCommand(commandProcessor);
        AbstractClientCommand historyCommand = new HistoryCommand(ServerConfig.getClientCommandHistory().getHistory(), commandProcessor);
        AbstractClientCommand removeAnyByNumberOfParticipantsCommand = new RemoveAnyByNumberOfParticipantsCommand(commandProcessor);
        AbstractClientCommand minByStudioCommand = new MinByStudioCommand(commandProcessor);
        AbstractClientCommand countLessThanNumberOfParticipantsCommand = new CountLessThatNumberOfParticipantsCommand(commandProcessor);
        AbstractServerCommand helpServerCommand = new ServerHelpCommand(ServerConfig.getServerAvailableCommands());
        AbstractServerCommand exitServerCommand = new ServerExitCommand();
        AbstractServerCommand historyServerCommand = new ServerHistoryCommand(ServerConfig.getClientCommandHistory().getHistory());

        ServerConfig.getClientAvailableCommands().put(infoCommand.getName(), infoCommand);
        ServerConfig.getClientAvailableCommands().put(showCommand.getName(), showCommand);
        ServerConfig.getClientAvailableCommands().put(addCommand.getName(), addCommand);
        ServerConfig.getClientAvailableCommands().put(updateCommand.getName(), updateCommand);
        ServerConfig.getClientAvailableCommands().put(removeByIdCommand.getName(), removeByIdCommand);
        ServerConfig.getClientAvailableCommands().put(clearCommand.getName(), clearCommand);
        ServerConfig.getClientAvailableCommands().put(addIfMaxCommand.getName(), addIfMaxCommand);
        ServerConfig.getClientAvailableCommands().put(removeGreaterCommand.getName(), removeGreaterCommand);
        ServerConfig.getClientAvailableCommands().put(historyCommand.getName(), historyCommand);
        ServerConfig.getClientAvailableCommands().put(removeAnyByNumberOfParticipantsCommand.getName(), removeAnyByNumberOfParticipantsCommand);
        ServerConfig.getClientAvailableCommands().put(minByStudioCommand.getName(), minByStudioCommand);
        ServerConfig.getClientAvailableCommands().put(countLessThanNumberOfParticipantsCommand.getName(), countLessThanNumberOfParticipantsCommand);

        ServerConfig.getServerAvailableCommands().put(helpServerCommand.getName(), helpServerCommand);
        ServerConfig.getServerAvailableCommands().put(exitServerCommand.getName(), exitServerCommand);
        ServerConfig.getServerAvailableCommands().put(historyServerCommand.getName(), historyServerCommand);
    }

    public CommandResponse executeClientCommand(CommandRequest request) {
        ServerConfig.getClientCommandHistory().pushCommand(request.getCurrentTime().format(DateTimeFormatter.ofPattern("HH:mm:ss"))
                + " " + request.getClientInfo() + ": " + request.getCommandName());
        return ServerConfig.getClientAvailableCommands().get(request.getCommandName()).executeClientCommand(request);
    }

    public String executeServerCommand(String commandName) {
        if (ServerConfig.getServerAvailableCommands().containsKey(commandName)) {
            return ServerConfig.getServerAvailableCommands().get(commandName).executeServerCommand();
        } else {
            return TextColoring.getRedText("There is no such command, type HELP to get list on commands");
        }
    }
}
