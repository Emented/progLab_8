package emented.lab8FX.server.util;

import emented.lab8FX.common.entities.MusicBand;
import emented.lab8FX.common.exceptions.CollectionIsEmptyException;
import emented.lab8FX.common.exceptions.DatabaseException;
import emented.lab8FX.common.util.Request;
import emented.lab8FX.common.util.requests.CommandRequest;
import emented.lab8FX.common.util.responses.CommandResponse;
import emented.lab8FX.server.db.DBManager;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class CommandProcessor {

    private final DBManager dbManager;
    private final CollectionManager collectionManager;


    public CommandProcessor(DBManager dbManager, CollectionManager collectionManager) {
        this.dbManager = dbManager;
        this.collectionManager = collectionManager;
    }

    public CommandResponse add(CommandRequest request) {
        try {
            if (!dbManager.validateUser(request.getUsername(), request.getPassword())) {
                return new CommandResponse(false, "Login and password mismatch");
            }
            MusicBand bandToAdd = request.getBandArgument();
            Long id = dbManager.addElement(bandToAdd, request.getUsername());
            bandToAdd.setId(id);
            collectionManager.addMusicBand(bandToAdd);
            return new CommandResponse(true, "Element was successfully added to collection with ID: "
                    + id);
        } catch (DatabaseException e) {
            return new CommandResponse(false, e.getMessage());
        }
    }

    public CommandResponse removeById(CommandRequest request) {
        try {
            if (!dbManager.validateUser(request.getUsername(), request.getPassword())) {
                return new CommandResponse(false, "Login and password mismatch");
            }
            if (!dbManager.checkBandExistence(request.getNumericArgument())) {
                return new CommandResponse(false, "There is no element with such ID");
            }
            if (dbManager.removeById(request.getNumericArgument(), request.getUsername())) {
                collectionManager.removeBandById(request.getNumericArgument());
                return new CommandResponse(true, "Element with ID " + request.getNumericArgument()
                        + " was removed from the collection");
            } else {
                return new CommandResponse(false, "Element was created by another user, you don't "
                        + "have permission to remove it");
            }
        } catch (DatabaseException e) {
            return new CommandResponse(false, e.getMessage());
        }
    }

    public CommandResponse updateById(CommandRequest request) {
        try {
            if (!dbManager.validateUser(request.getUsername(), request.getPassword())) {
                return new CommandResponse(false, "Login and password mismatch");
            }
            if (!dbManager.checkBandExistence(request.getNumericArgument())) {
                return new CommandResponse(false, "There is no element with such ID");
            }
            if (dbManager.updateById(request.getBandArgument(), request.getNumericArgument(), request.getUsername())) {
                collectionManager.updateById(request.getNumericArgument(), request.getBandArgument());
                return new CommandResponse(true, "Element with ID " + request.getNumericArgument()
                        + " was successfully updated");
            } else {
                return new CommandResponse(false, "Element was created by another user, you don't "
                        + "have permission to update it");
            }
        } catch (DatabaseException e) {
            return new CommandResponse(false, e.getMessage());
        }
    }

    public CommandResponse clear(CommandRequest request) {
        try {
            if (!dbManager.validateUser(request.getUsername(), request.getPassword())) {
                return new CommandResponse(false, "Login and password mismatch");
            }
            List<Long> deletedIDs = dbManager.clear(request.getUsername());
            if (deletedIDs.isEmpty()) {
                return new CommandResponse(false, "You don't have elements in this collection!");
            } else {
                deletedIDs.forEach(collectionManager::removeBandById);
                return new CommandResponse(true, "Your elements were removed from the collection, their IDs:", deletedIDs);
            }
        } catch (DatabaseException e) {
            return new CommandResponse(false, e.getMessage());
        }
    }

    public CommandResponse removeGreater(CommandRequest request) {
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

    public CommandResponse show(CommandRequest request) {
        try {
            if (!dbManager.validateUser(request.getUsername(), request.getPassword())) {
                return new CommandResponse(false, "Login and password mismatch");
            }
            if (collectionManager.getMusicBands().isEmpty()) {
                return new CommandResponse(false, "Collection is empty");
            } else {
                List<Long> ids = dbManager.getIdsOfUsersElements(request.getUsername());
                return new CommandResponse(true, "Elements of collection:",
                        collectionManager.getUsersElements(ids),
                        collectionManager.getAlienElements(ids));
            }
        } catch (DatabaseException e) {
            return new CommandResponse(false, e.getMessage());
        }
    }

    public CommandResponse info(CommandRequest request) {
        try {
            if (!dbManager.validateUser(request.getUsername(), request.getPassword())) {
                return new CommandResponse(false, "Login and password mismatch");
            }
            return new CommandResponse(true, "Info about collection:\n" + collectionManager.returnInfo());
        } catch (DatabaseException e) {
            return new CommandResponse(false, e.getMessage());
        }
    }

    public CommandResponse countLessThenNumberOfParticipants(CommandRequest request) {
        try {
            if (!dbManager.validateUser(request.getUsername(), request.getPassword())) {
                return new CommandResponse(false, "Login and password mismatch");
            }
            int result = collectionManager.countLessThanNumberOfParticipants(request.getNumericArgument());
            return new CommandResponse(true, "Groups with fewer participants than "
                    + request.getNumericArgument()
                    + ": " + result);
        } catch (DatabaseException e) {
            return new CommandResponse(false, e.getMessage());
        }
    }

    public CommandResponse removeAnyByNumberOfParticipants(CommandRequest request) {
        try {
            if (!dbManager.validateUser(request.getUsername(), request.getPassword())) {
                return new CommandResponse(false, "Login and password mismatch");
            }
            List<Long> ids = collectionManager.returnIDbyNumberOFParticipants(request.getNumericArgument());
            if (ids.isEmpty()) {
                return new CommandResponse(false, "There is no band with number of participants equals "
                        + request.getNumericArgument());
            } else {
                for (Long id : ids) {
                    if (dbManager.removeById(id, request.getUsername())) {
                        collectionManager.removeBandById(id);
                        return new CommandResponse(true, "MusicBand with " + request.getNumericArgument()
                                + " participants and ID equals "
                                + id + " was removed");
                    }
                }
                return new CommandResponse(false, "There are no such elements, that belong to you "
                        + "in this collection with " + request.getNumericArgument() + " participants");
            }
        } catch (DatabaseException e) {
            return new CommandResponse(false, e.getMessage());
        }
    }

    public CommandResponse addIfMax(CommandRequest request) {
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

    public CommandResponse minByStudio(CommandRequest request) {
        try {
            if (!dbManager.validateUser(request.getUsername(), request.getPassword())) {
                return new CommandResponse(false, "Login and password mismatch");
            }
            try {
                return new CommandResponse(true, "Minimal element:", collectionManager.returnMinByStudio());
            } catch (CollectionIsEmptyException e) {
                return new CommandResponse(false, e.getMessage());
            }
        } catch (DatabaseException e) {
            return new CommandResponse(false, e.getMessage());
        }
    }

    public CommandResponse history(CommandRequest request, ArrayDeque<String> queueOfCommands) {
        try {
            if (!dbManager.validateUser(request.getUsername(), request.getPassword())) {
                return new CommandResponse(false, "Login and password mismatch");
            }
            StringBuilder sb = new StringBuilder();
            if (!queueOfCommands.isEmpty()) {
                for (String name : queueOfCommands) {
                    sb.append(name).append("\n");
                }
            } else {
                sb.append("History is empty");
            }
            sb = new StringBuilder(sb.substring(0, sb.length() - 1));
            return new CommandResponse(true, sb.toString());
        } catch (DatabaseException e) {
            return new CommandResponse(false, e.getMessage());
        }
    }
}
