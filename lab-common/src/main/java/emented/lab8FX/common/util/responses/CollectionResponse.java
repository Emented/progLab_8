package emented.lab8FX.common.util.responses;

import emented.lab8FX.common.abstractions.AbstractResponse;
import emented.lab8FX.common.entities.MusicBand;

import java.util.Set;

public class CollectionResponse extends AbstractResponse {

    private final Set<MusicBand> collection;

    public CollectionResponse(boolean isSuccess, String message, Set<MusicBand> collection) {
        super(isSuccess, message);
        this.collection = collection;
    }

    public Set<MusicBand> getCollection() {
        return collection;
    }
}
