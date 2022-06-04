package emented.lab8FX.server.clientcommands;

import emented.lab8FX.common.exceptions.DatabaseException;
import emented.lab8FX.common.util.requests.CommandRequest;
import emented.lab8FX.common.util.responses.CommandResponse;
import emented.lab8FX.server.abstractions.AbstractClientCommand;
import emented.lab8FX.server.db.DBManager;
import emented.lab8FX.server.util.CollectionManager;

import java.util.ArrayList;
import java.util.List;

public class RemoveGreaterCommand extends AbstractClientCommand {

    private final DBManager dbManager;
    private final CollectionManager collectionManager;

    public RemoveGreaterCommand(DBManager dbManager, CollectionManager collectionManager) {
        super("remove_greater",
                0,
                "remove all items from the collection that exceed the specified");
        this.dbManager = dbManager;
        this.collectionManager = collectionManager;
    }

    @Override
    public CommandResponse executeClientCommand(CommandRequest request) {
        try {
            if (!dbManager.validateUser(request.getUsername(), request.getPassword())) {
                return new CommandResponse(false, "Login and password mismatch");
            }
            List<Long> ids = collectionManager.returnIDsOfGreater(request.getBandArgument());
            if (ids.isEmpty()) {
                return new CommandResponse(false, "There are no such elements in collection");
            } else {
                List<Long> removedIDs = new ArrayList<>();
                for (Long id : ids) {
                    if (dbManager.removeById(id, request.getUsername())) {
                        removedIDs.add(id);
                        collectionManager.removeBandById(id);
                    }
                }
                if (removedIDs.isEmpty()) {
                    return new CommandResponse(false, "There are no such elements, that belong to you in collection");
                } else {
                    return new CommandResponse(true, "Elements with this IDs were removed from the collection", removedIDs);
                }
            }
        } catch (DatabaseException e) {
            return new CommandResponse(false, e.getMessage());
        }
    }
}
