package emented.lab8FX.client.util.validators;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public final class ConnectionValidator {

    private ConnectionValidator() {
    }

    public static String validateAddress(String address) throws IllegalArgumentException {
        if ("".equals(address)) {
            return null;
        }
        try {
            InetAddress.getByName(address);
            return address;
        } catch (UnknownHostException e) {
            throw new IllegalArgumentException("Wrong address!");
        }
    }

    public static Integer validatePort(String port) throws IllegalArgumentException {
        if ("".equals(port)) {
            return null;
        }
        try {
            int portNum = Integer.parseInt(port);
            if (portNum <= 0 || portNum > 65535) {
                throw new IllegalArgumentException("Port should be a number between 1 and 65535!");
            }
            return portNum;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Port should be a number!");
        }
    }

    public static List<String> validateConnection(String address, String port) {
        List<String> errorList = new ArrayList<>();
        try {
            validateAddress(address);
            errorList.add(null);
        } catch (IllegalArgumentException e) {
            errorList.add(e.getMessage());
        }
        try {
            validatePort(port);
            errorList.add(null);
        } catch (IllegalArgumentException e) {
            errorList.add(e.getMessage());
        }
        return errorList;
    }
}
