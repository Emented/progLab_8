package emented.lab8FX.common.util;


import emented.lab8FX.common.abstractions.AbstractRequest;
import emented.lab8FX.common.abstractions.AbstractResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public final class DeSerializer {

    private DeSerializer() {
    }

    public static AbstractRequest deSerializeRequest(byte[] acceptedBuf) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(acceptedBuf);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        AbstractRequest request = (AbstractRequest) objectInputStream.readObject();
        byteArrayInputStream.close();
        objectInputStream.close();
        return request;
    }

    public static AbstractResponse deSerializeResponse(byte[] acceptedBuf) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(acceptedBuf);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        AbstractResponse response = (AbstractResponse) objectInputStream.readObject();
        byteArrayInputStream.close();
        objectInputStream.close();
        return response;
    }

}
