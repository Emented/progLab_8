package emented.lab8FX.client.util;

import emented.lab8FX.common.abstractions.AbstractRequest;
import emented.lab8FX.common.abstractions.AbstractResponse;
import emented.lab8FX.common.util.DeSerializer;
import emented.lab8FX.common.util.Serializer;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;

public class ClientSocketWorker {

    private final int defaultPort = 228;
    private final int timeToResponse = 4000;
    private final DatagramSocket datagramSocket;
    private int port;
    private String address = "localhost";
    private InetAddress serverAddress;

    public ClientSocketWorker() throws UnknownHostException, SocketException {
        port = defaultPort;
        datagramSocket = new DatagramSocket();
        serverAddress = InetAddress.getByName(address);
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) throws UnknownHostException {
        this.address = address;
        serverAddress = InetAddress.getByName(address);
    }

    public void sendRequest(AbstractRequest request) throws IOException {
        ByteBuffer byteBuffer = Serializer.serializeRequest(request);
        byte[] bufferToSend = byteBuffer.array();
        DatagramPacket datagramPacket = new DatagramPacket(bufferToSend, bufferToSend.length, serverAddress, port);
        datagramSocket.send(datagramPacket);
    }

    public AbstractResponse receiveResponse() throws ClassNotFoundException, IOException {
        datagramSocket.setSoTimeout(timeToResponse);
        int receivedSize = datagramSocket.getReceiveBufferSize();
        byte[] byteBuf = new byte[receivedSize];
        DatagramPacket dpFromServer = new DatagramPacket(byteBuf, byteBuf.length);
        datagramSocket.receive(dpFromServer);
        byte[] bytesFromServer = dpFromServer.getData();
        return DeSerializer.deSerializeResponse(bytesFromServer);
    }

    public synchronized AbstractResponse proceedTransaction(AbstractRequest request) throws ClassNotFoundException, IOException {
        sendRequest(request);
        return receiveResponse();
    }


    public void closeSocket() {
        datagramSocket.close();
    }
}
