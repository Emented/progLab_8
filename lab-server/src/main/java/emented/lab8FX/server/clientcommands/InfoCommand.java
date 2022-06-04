package emented.lab8FX.server.clientcommands;

import emented.lab8FX.common.exceptions.DatabaseException;
import emented.lab8FX.common.util.requests.CommandRequest;
import emented.lab8FX.common.util.responses.CommandResponse;
import emented.lab8FX.server.abstractions.AbstractClientCommand;
import emented.lab8FX.server.db.DBManager;
import emented.lab8FX.server.util.CollectionManager;

public class InfoCommand extends AbstractClientCommand {

    private final DBManager dbManager;
    private final CollectionManager collectionManager;

    public InfoCommand(DBManager dbManager, CollectionManager collectionManager) {
        super("info",
                0,
                "display information about the collection");
        this.dbManager = dbManager;
        this.collectionManager = collectionManager;
    }

    @Override
    public CommandResponse executeClientCommand(CommandRequest request) {
        try {
            if (!dbManager.validateUser(request.getUsername(), request.getPassword())) {
                return new CommandResponse(false, "Login and password mismatch");
            }
            return new CommandResponse(true, "Info about collection:\n" + collectionManager.returnInfo());
        } catch (DatabaseException e) {
            return new CommandResponse(false, e.getMessage());
        }
    }
}
