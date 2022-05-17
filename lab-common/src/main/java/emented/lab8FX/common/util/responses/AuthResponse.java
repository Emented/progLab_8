package emented.lab8FX.common.util.responses;

import emented.lab8FX.common.abstractions.AbstractResponse;

import java.io.Serializable;

public class AuthResponse extends AbstractResponse implements Serializable {

    public AuthResponse(boolean isSuccess, String message) {
        super(isSuccess, message);
    }
}
