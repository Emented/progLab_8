package emented.lab8FX.client.util;

public enum PathToViews {

    CONNECTION_VIEW("/fxml/connection.fxml"),
    REGISTRATION_VIEW("/fxml/registration.fxml"),
    LOGIN_VIEW("/fxml/login.fxml"),
    MAIN_VIEW("/fxml/main.fxml"),
    ADD_VIEW("/fxml/add.fxml"),
    REMOVE_GREATER("/fxml/remove_greater.fxml"),
    REMOVE_BY_ID_VIEW("/fxml/remove_by_id.fxml"),
    REMOVE_ANY("/fxml/remove_any.fxml");

    private final String path;

    PathToViews(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
