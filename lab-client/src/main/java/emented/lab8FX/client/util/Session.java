package emented.lab8FX.client.util;

public class Session {

    private final String username;

    private final String password;

    public Session(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

