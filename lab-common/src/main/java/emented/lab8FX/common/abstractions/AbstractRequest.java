package emented.lab8FX.common.abstractions;

import java.io.Serializable;
import java.time.LocalTime;

public class AbstractRequest implements Serializable {

    private final String clientInfo;

    private final LocalTime currentTime;

    private Long requestId;

    public AbstractRequest(String clientInfo) {
        this.clientInfo = clientInfo;
        currentTime = LocalTime.now();
    }

    public String getClientInfo() {
        return clientInfo;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public LocalTime getCurrentTime() {
        return currentTime;
    }

    public Class<?> getType() {
        return this.getClass();
    }
}
