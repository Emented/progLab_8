package emented.lab8FX.common.util.requests;

import emented.lab8FX.common.abstractions.AbstractRequest;

import java.io.Serializable;

public class LoginRequest extends AbstractRequest implements Serializable {

    private String username;

    private String password;

    public LoginRequest(String clientInfo, String username, String password) {
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
