package emented.lab8FX.common.util.requests;

import emented.lab8FX.common.abstractions.AbstractRequest;

public class ConnectionRequest extends AbstractRequest {

    public ConnectionRequest(String clientInfo) {
        super(clientInfo);
    }

    @Override
    public String toString() {
        return "Connection request";
    }
}
