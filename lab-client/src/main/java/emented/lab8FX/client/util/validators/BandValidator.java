package emented.lab8FX.client.util.validators;

import java.util.ArrayList;
import java.util.List;

public class BandValidator {

    private BandValidator() {

    }

    public static String validateName(String name) {
        if (name.length() < 5) {
            throw new IllegalArgumentException("Login should be longer than 5 symbols!");
        }
        return name;
    }

    public static double validateX(String x) throws IllegalArgumentException {
        double xNum;
        try {
            xNum = Double.parseDouble(x);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("X should be a number!");
        }
        if (xNum < 0 || xNum > 947) {
            throw new IllegalArgumentException("X coordinate should be a number between 0 and 947!");
        }
        return xNum;
    }

    public static Float validateY(String y) throws IllegalArgumentException {
        float yNum;
        try {
            yNum = Float.parseFloat(y);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Y should be a number!");
        }
        if (yNum < 0 || yNum > 104) {
            throw new IllegalArgumentException("Y coordinate should be a number between 0 and 104!");
        }
        return yNum;
    }

    public static Long validateNumber(String numberOfParticipants) throws IllegalArgumentException {
        long number;
        try {
            number = Long.parseLong(numberOfParticipants);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Number of participants should be a number!");
        }
        if (number <= 0) {
            throw new IllegalArgumentException("Number of participants should be grater then 0!");
        }
        return number;
    }

    public static List<String> validateBand(String name, String x, String y, String number) {
        List<String> errorList = new ArrayList<>();
        try {
            validateName(name);
            errorList.add(null);
        } catch (IllegalArgumentException e) {
            errorList.add(e.getMessage());
        }
        try {
            validateX(x);
            errorList.add(null);
        } catch (IllegalArgumentException e) {
            errorList.add(e.getMessage());
        }
        try {
            validateY(y);
            errorList.add(null);
        } catch (IllegalArgumentException e) {
            errorList.add(e.getMessage());
        }
        try {
            validateNumber(number);
            errorList.add(null);
        } catch (IllegalArgumentException e) {
            errorList.add(e.getMessage());
        }
        errorList.add(null);
        errorList.add(null);
        return errorList;
    }
}
