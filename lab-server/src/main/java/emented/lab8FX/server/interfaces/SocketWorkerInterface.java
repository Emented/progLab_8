package emented.lab8FX.server.interfaces;

import emented.lab8FX.common.abstractions.AbstractResponse;
import emented.lab8FX.common.util.Response;
import emented.lab8FX.server.util.RequestWithAddress;

import java.io.IOException;
import java.net.SocketAddress;

public interface SocketWorkerInterface {
    RequestWithAddress listenForRequest() throws IOException, ClassNotFoundException;
    void sendResponse(AbstractResponse response, SocketAddress address) throws IOException;
    void stopSocketWorker() throws IOException;
}
