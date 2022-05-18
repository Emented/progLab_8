package emented.lab8FX.common.util.responses;

import emented.lab8FX.common.abstractions.AbstractResponse;

public class AuthResponse extends AbstractResponse {

    public AuthResponse(boolean isSuccess, String message) {
        super(isSuccess, message);
    }
}
