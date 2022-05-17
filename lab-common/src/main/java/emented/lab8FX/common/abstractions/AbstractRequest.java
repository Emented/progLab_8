package emented.lab8FX.common.abstractions;

import java.io.Serializable;
import java.time.LocalTime;

public class AbstractRequest implements Serializable {

    private final String clientInfo;

    private final LocalTime currentTime;

    public AbstractRequest(String clientInfo) {
        this.clientInfo = clientInfo;
        currentTime = LocalTime.now();
    }

    public String getClientInfo() {
        return clientInfo;
    }

    public LocalTime getCurrentTime() {
        return currentTime;
    }

    public Class<?> getType() {
        return this.getClass();
    }
}
