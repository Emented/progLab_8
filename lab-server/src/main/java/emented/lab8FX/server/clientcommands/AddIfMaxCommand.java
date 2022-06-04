package emented.lab8FX.server.clientcommands;


import emented.lab8FX.common.entities.MusicBand;
import emented.lab8FX.common.exceptions.DatabaseException;
import emented.lab8FX.common.util.requests.CommandRequest;
import emented.lab8FX.common.util.responses.CommandResponse;
import emented.lab8FX.server.abstractions.AbstractClientCommand;
import emented.lab8FX.server.db.DBManager;
import emented.lab8FX.server.util.CollectionManager;

public class AddIfMaxCommand extends AbstractClientCommand {

    private final DBManager dbManager;
    private final CollectionManager collectionManager;

    public AddIfMaxCommand(DBManager dbManager, CollectionManager collectionManager) {
        super("add_if_max",
                0,
                "add a new item to the collection if its value exceeds the value of the largest item in this collection");
        this.dbManager = dbManager;
        this.collectionManager = collectionManager;
    }

    @Override
    public CommandResponse executeClientCommand(CommandRequest request) {
        try {
            if (!dbManager.validateUser(request.getUsername(), request.getPassword())) {
                return new CommandResponse(false, "Login and password mismatch");
            }
            MusicBand bandToAdd = request.getBandArgument();
            if (collectionManager.checkMax(bandToAdd)) {
                Long id = dbManager.addElement(bandToAdd, request.getUsername());
                bandToAdd.setId(id);
                collectionManager.addMusicBand(bandToAdd);
                return new CommandResponse(true, "Element was successfully added to collection with ID: "
                        + id);
            } else {
                return new CommandResponse(false, "Element is not max");
            }
        } catch (DatabaseException e) {
            return new CommandResponse(false, e.getMessage());
        }
    }
}
