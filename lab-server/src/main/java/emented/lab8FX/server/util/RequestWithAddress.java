package emented.lab8FX.server.util;


import emented.lab8FX.common.util.Request;

import java.net.SocketAddress;

public class RequestWithAddress {
    private final Request request;
    private final SocketAddress socketAddress;

    public RequestWithAddress(Request request, SocketAddress socketAddress) {
        this.request = request;
        this.socketAddress = socketAddress;
    }

    public Request getRequest() {
        return request;
    }

    public SocketAddress getSocketAddress() {
        return socketAddress;
    }
}
