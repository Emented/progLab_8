package emented.lab8FX.common.util.responses;

import emented.lab8FX.common.abstractions.AbstractResponse;

import java.io.Serializable;

public class ConnectionResponse extends AbstractResponse implements Serializable {
    public ConnectionResponse(boolean isSuccess, String message) {
        super(isSuccess, message);
    }
}
