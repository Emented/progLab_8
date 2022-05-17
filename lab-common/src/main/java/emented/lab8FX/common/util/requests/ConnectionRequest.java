package emented.lab8FX.common.util.requests;

import emented.lab8FX.common.abstractions.AbstractRequest;

import java.io.Serializable;

public class ConnectionRequest extends AbstractRequest implements Serializable {

    public ConnectionRequest(String clientInfo) {
        super(clientInfo);
    }

    @Override
    public String toString() {
        return "Connection request";
    }
}
