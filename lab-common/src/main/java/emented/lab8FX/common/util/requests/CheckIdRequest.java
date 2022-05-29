package emented.lab8FX.common.util.requests;

import emented.lab8FX.common.abstractions.AbstractRequest;

public class CheckIdRequest extends AbstractRequest {

    private final Long id;

    private final String username;

    private final String password;

    public CheckIdRequest(String clientInfo, Long id, String username, String password) {
        super(clientInfo);
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Check id request";
    }
}
