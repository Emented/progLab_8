package emented.lab8FX.common.util;

import emented.lab8FX.common.abstractions.AbstractRequest;
import emented.lab8FX.common.abstractions.AbstractResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;

public final class Serializer {

    private Serializer() {
    }

    public static ByteBuffer serializeRequest(AbstractRequest request) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(request);
        objectOutputStream.flush();
        ByteBuffer bufToSend = ByteBuffer.wrap(byteArrayOutputStream.toByteArray());
        objectOutputStream.close();
        byteArrayOutputStream.close();
        return bufToSend;
    }

    public static ByteBuffer serializeResponse(AbstractResponse response) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(response);
        objectOutputStream.flush();
        ByteBuffer bufToSend = ByteBuffer.wrap(byteArrayOutputStream.toByteArray());
        objectOutputStream.close();
        byteArrayOutputStream.close();
        return bufToSend;
    }

}
