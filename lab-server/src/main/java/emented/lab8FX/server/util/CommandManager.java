package emented.lab8FX.server.util;

import emented.lab8FX.common.exceptions.DatabaseException;
import emented.lab8FX.common.util.TextColoring;
import emented.lab8FX.common.util.requests.CheckIdRequest;
import emented.lab8FX.common.util.requests.CollectionRequest;
import emented.lab8FX.common.util.requests.CommandRequest;
import emented.lab8FX.common.util.responses.CheckIdResponse;
import emented.lab8FX.common.util.responses.CollectionResponse;
import emented.lab8FX.common.util.responses.CommandResponse;
import emented.lab8FX.server.ServerConfig;
import emented.lab8FX.server.abstractions.AbstractClientCommand;
import emented.lab8FX.server.abstractions.AbstractServerCommand;
import emented.lab8FX.server.clientcommands.*;
import emented.lab8FX.server.db.DBManager;
import emented.lab8FX.server.servercommands.ServerExitCommand;
import emented.lab8FX.server.servercommands.ServerHelpCommand;
import emented.lab8FX.server.servercommands.ServerHistoryCommand;

import java.time.format.DateTimeFormatter;

public class CommandManager {
    private final DBManager dbManager;

    private final CollectionManager collectionManager;

    public CommandManager(DBManager dbManager, CollectionManager collectionManager) {
        this.dbManager = dbManager;
        this.collectionManager = collectionManager;
        setCommands();
    }

    private void setCommands() {
        AbstractClientCommand infoCommand = new InfoCommand(dbManager, collectionManager);
        AbstractClientCommand showCommand = new ShowCommand(dbManager, collectionManager);
        AbstractClientCommand addCommand = new AddCommand(dbManager, collectionManager);
        AbstractClientCommand updateCommand = new UpdateCommand(dbManager, collectionManager);
        AbstractClientCommand removeByIdCommand = new RemoveByIdCommand(dbManager, collectionManager);
        AbstractClientCommand clearCommand = new ClearCommand(dbManager, collectionManager);
        AbstractClientCommand addIfMaxCommand = new AddIfMaxCommand(dbManager, collectionManager);
        AbstractClientCommand removeGreaterCommand = new RemoveGreaterCommand(dbManager, collectionManager);
        AbstractClientCommand historyCommand = new HistoryCommand(ServerConfig.getClientCommandHistory().getHistory(), dbManager);
        AbstractClientCommand removeAnyByNumberOfParticipantsCommand = new RemoveAnyByNumberOfParticipantsCommand(dbManager, collectionManager);
        AbstractClientCommand minByStudioCommand = new MinByStudioCommand(dbManager, collectionManager);
        AbstractClientCommand countLessThanNumberOfParticipantsCommand = new CountLessThanNumberOfParticipantsCommand(dbManager, collectionManager);
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

    public CheckIdResponse checkId(CheckIdRequest request) {
        try {
            if (!dbManager.validateUser(request.getUsername(), request.getPassword())) {
                return new CheckIdResponse(false, "Login and password mismatch");
            }
            if (dbManager.checkBandExistence(request.getId())) {
                return new CheckIdResponse(true, "Band with this ID exists");
            } else {
                return new CheckIdResponse(false, "Band with this ID doesn't exist");
            }
        } catch (DatabaseException e) {
            return new CheckIdResponse(false, e.getMessage());
        }
    }

    public CollectionResponse returnCollection(CollectionRequest commandRequest) {
        try {
            if (!dbManager.validateUser(commandRequest.getUsername(), commandRequest.getPassword())) {
                return new CollectionResponse(false, "Login and password mismatch", null, null);
            }
            Thread.sleep(10000);
            return new CollectionResponse(true, "Ok", collectionManager.getMusicBands(), dbManager.getIdsOfUsersElements(commandRequest.getUsername()));
        } catch (DatabaseException e) {
            return new CollectionResponse(false, e.getMessage(), null, null);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
