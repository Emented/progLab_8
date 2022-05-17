package emented.lab8FX.common.util.responses;

import emented.lab8FX.common.abstractions.AbstractResponse;
import emented.lab8FX.common.entities.MusicBand;
import emented.lab8FX.common.util.SizeAnalyzer;
import emented.lab8FX.common.util.TextColoring;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CommandResponse extends AbstractResponse implements Serializable {

    private final MusicBand bandToResponse;
    private final Set<MusicBand> yourElementsOfCollection;
    private final Set<MusicBand> alienElementsOfCollection;
    private final List<Long> listOfIds;

    public CommandResponse(boolean isSuccess, String message) {
        super(isSuccess, message);
        this.bandToResponse = null;
        this.yourElementsOfCollection = null;
        this.alienElementsOfCollection = null;
        this.listOfIds = null;
    }

    public CommandResponse(boolean isSuccess, String message, MusicBand bandToResponse) {
        super(isSuccess, message);
        this.bandToResponse = bandToResponse;
        this.yourElementsOfCollection = null;
        this.alienElementsOfCollection = null;
        this.listOfIds = null;
    }

    public CommandResponse(boolean isSuccess, String message, List<Long> listOfIds) {
        super(isSuccess, message);
        this.bandToResponse = null;
        this.yourElementsOfCollection = null;
        this.alienElementsOfCollection = null;
        this.listOfIds = listOfIds;
    }

    public CommandResponse(boolean isSuccess, String message, Set<MusicBand> yourElementsOfCollection, Set<MusicBand> alienElementsOfCollection) {
        super(isSuccess, message);
        this.bandToResponse = null;
        this.yourElementsOfCollection = yourElementsOfCollection;
        this.alienElementsOfCollection = alienElementsOfCollection;
        this.listOfIds = null;
    }

    public String getInfoAboutResponse() {
        return "Response contains: " + (super.getMessage() == null ? "" : "message")
                + (bandToResponse == null ? "" : ", musicband")
                + (yourElementsOfCollection == null ? "" : ", collection");
    }

    @Override
    public String toString() {
        StringBuilder collection = new StringBuilder();
        StringBuilder ids = new StringBuilder();
        if (yourElementsOfCollection != null) {
            List<MusicBand> sortedBands = new ArrayList<>(yourElementsOfCollection);
            sortedBands = sortedBands.stream().sorted(Comparator.comparing(SizeAnalyzer::getSizeOfBand).reversed()).collect(Collectors.toList());
            collection.append(TextColoring.getGreenText("Your elements:\n"));
            for (MusicBand m : sortedBands) {
                collection.append(m.toString()).append("\n");
            }
        } else {
            collection.append(TextColoring.getGreenText("You don't have elements in this collection!\n"));
        }
        if (alienElementsOfCollection != null) {
            List<MusicBand> sortedBands = new ArrayList<>(alienElementsOfCollection);
            sortedBands = sortedBands.stream().sorted(Comparator.comparing(SizeAnalyzer::getSizeOfBand).reversed()).collect(Collectors.toList());
            collection.append(TextColoring.getGreenText("Another user's elements:\n"));
            for (MusicBand m : sortedBands) {
                collection.append(m.toString()).append("\n");
            }
            collection = new StringBuilder(collection.substring(0, collection.length() - 1));
        }  else {
            collection.append(TextColoring.getGreenText("Another users don't have elements in this collection!"));
        }
        if (listOfIds != null) {
            for (Long id : listOfIds) {
                ids.append(id).append(", ");
            }
            ids = new StringBuilder(ids.substring(0, ids.length() - 2));
        }
        return (super.getMessage() == null ? "" : super.getMessage())
                + (bandToResponse == null ? "" : "\n" + bandToResponse)
                + ((yourElementsOfCollection == null && alienElementsOfCollection == null) ? "" : "\n" + collection)
                + ((listOfIds == null) ? "" : "\n" + ids);
    }
}
