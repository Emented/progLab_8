package emented.lab8FX.client.util.validators;

import java.util.ArrayList;
import java.util.List;

public class NumberValidator {

    private NumberValidator() {

    }

    public static List<String> validateId(String id) {
        List<String> errorList = new ArrayList<>();
        try {
            long idNum;
            try {
                idNum = Long.parseLong(id);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Id should be a number!");
            }
            if (idNum <= 0) {
                throw new IllegalArgumentException("Id should be greater than 0!");
            }
            errorList.add(null);
        } catch (IllegalArgumentException e) {
            errorList.add(e.getMessage());
        }
        return errorList;
    }

    public static List<String> validateNumber(String number) {
        List<String> errorList = new ArrayList<>();
        try {
            long num;
            try {
                num = Long.parseLong(number);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Amount of participants should be a number!");
            }
            if (num <= 0) {
                throw new IllegalArgumentException("Amount of participants should be greater than 0!");
            }
            errorList.add(null);
        } catch (IllegalArgumentException e) {
            errorList.add(e.getMessage());
        }
        return errorList;
    }

    public static Long getValidatedId(String id) {
        long idNum;
        try {
            idNum = Long.parseLong(id);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Id should be a number!");
        }
        if (idNum <= 0) {
            throw new IllegalArgumentException("Id should be greater than 0!");
        }
        return idNum;
    }

    public static Long getValidatedNumberOfParticipants(String number) {
        long num;
        try {
            num = Long.parseLong(number);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Amount of participants should be a number!");
        }
        if (num <= 0) {
            throw new IllegalArgumentException("Amount of participants should be greater than 0!");
        }
        return num;
    }
}
