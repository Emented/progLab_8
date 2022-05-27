package emented.lab8FX.client.util;

public enum PathToViews {

    CONNECTION_VIEW("/fxml/connection.fxml"),
    REGISTRATION_VIEW("/fxml/registration.fxml"),
    LOGIN_VIEW("/fxml/login.fxml"),
    MAIN_VIEW("/fxml/main.fxml"),
    ADD_VIEW("/fxml/add.fxml");

    private final String path;

    PathToViews(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
