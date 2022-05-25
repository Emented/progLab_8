package emented.lab8FX.client.exceptions;

import javafx.scene.control.Alert;

public class ExceptionWithAlert extends Exception {

    public ExceptionWithAlert(String message) {
        super(message);
    }

    public void showAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR, getMessage());
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}
