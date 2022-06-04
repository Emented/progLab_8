package emented.lab8FX.client.util.validators;

import java.util.ArrayList;
import java.util.List;

public final class UserValidotor {

    private UserValidotor() {

    }

    private static void validateUsername(String username) {
        if (username.length() < 5) {
            throw new IllegalArgumentException("user_exception.small_username");
        }
        if (username.split(" ").length != 1) {
            throw new IllegalArgumentException("user_exception.splited_username");
        }
    }

    private static void validatePassword(String password) {
        if (password.length() < 5) {
            throw new IllegalArgumentException("user_exception.small_password");
        }
        if (password.split(" ").length != 1) {
            throw new IllegalArgumentException("user_exception.splited_password");
        }
    }

    private static void validatePasswordsMatch(String fPassword, String sPassword) {
        if (!fPassword.equals(sPassword)) {
            throw new IllegalArgumentException("user_exception.passwords_mismatch");
        }
    }

    public static List<String> validateLoginUser(String username, String password) {
        List<String> errorList = new ArrayList<>();
        try {
            validateUsername(username);
            errorList.add(null);
        } catch (IllegalArgumentException e) {
            errorList.add(e.getMessage());
        }
        try {
            validatePassword(password);
            errorList.add(null);
        } catch (IllegalArgumentException e) {
            errorList.add(e.getMessage());
        }
        return errorList;
    }

    public static List<String> validateRegisterUser(String username, String fPassword, String sPassword) {
        List<String> errorList = new ArrayList<>();
        try {
            validateUsername(username);
            errorList.add(null);
        } catch (IllegalArgumentException e) {
            errorList.add(e.getMessage());
        }
        try {
            validatePassword(fPassword);
            errorList.add(null);
        } catch (IllegalArgumentException e) {
            errorList.add(e.getMessage());
            errorList.add(e.getMessage());
            return errorList;
        }
        try {
            validatePasswordsMatch(fPassword, sPassword);
        } catch (IllegalArgumentException e) {
            errorList.add(e.getMessage());
        }
        return errorList;
    }

}
