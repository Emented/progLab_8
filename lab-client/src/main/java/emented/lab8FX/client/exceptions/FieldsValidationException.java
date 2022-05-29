package emented.lab8FX.client.exceptions;

import java.util.List;

public class FieldsValidationException extends Exception {

    private final List<String> errorList;

    public FieldsValidationException(List<String> errorList) {
        this.errorList = errorList;
    }

    public List<String> getErrorList() {
        return errorList;
    }
}
