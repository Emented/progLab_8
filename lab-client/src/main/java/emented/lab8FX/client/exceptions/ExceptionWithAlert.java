package emented.lab8FX.client.exceptions;

import javafx.scene.control.Alert;

public class ExceptionWithAlert extends Exception {

    private final boolean isFatal;

    public ExceptionWithAlert(String message) {
        super(message);
        isFatal = false;
    }

    public ExceptionWithAlert(String message, boolean isFatal) {
        super(message);
        this.isFatal = isFatal;
    }

    public boolean isFatal() {
        return isFatal;
    }

    public void showAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR, getMessage());
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}
