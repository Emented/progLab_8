package emented.lab8FX.common.util.requests;

import emented.lab8FX.common.abstractions.AbstractRequest;

public class LoginRequest extends AbstractRequest {

    private final String username;

    private final String password;

    public LoginRequest(String username, String password, String clientInfo) {
        super(clientInfo);
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "Login request";
    }
}
