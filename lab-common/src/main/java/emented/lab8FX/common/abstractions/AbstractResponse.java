package emented.lab8FX.common.abstractions;

import java.io.Serializable;

public class AbstractResponse implements Serializable {

    private boolean isSuccess;

    private String message;

    public AbstractResponse(boolean isSuccess, String message) {
        this.isSuccess = isSuccess;
        this.message = message;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public String getMessage() {
        return message;
    }
}
