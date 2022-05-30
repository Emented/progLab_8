package emented.lab8FX.common.util.responses;

import emented.lab8FX.common.abstractions.AbstractResponse;
import emented.lab8FX.common.entities.MusicBand;

import java.util.List;
import java.util.Set;

public class CollectionResponse extends AbstractResponse {

    private final Set<MusicBand> collection;

    private final List<Long> usersIds;

    public CollectionResponse(boolean isSuccess, String message, Set<MusicBand> collection, List<Long> usersIds) {
        super(isSuccess, message);
        this.collection = collection;
        this.usersIds = usersIds;
    }

    public Set<MusicBand> getCollection() {
        return collection;
    }

    public List<Long> getUsersIds() {
        return usersIds;
    }
}
