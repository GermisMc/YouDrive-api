package by.youdrive.commons;

import by.youdrive.commons.support.Representation;

import java.util.ArrayList;
import java.util.List;

public class ErrorMessages extends Representation {

    private List<String> errorMessages = new ArrayList<>();

    public ErrorMessages() {
    }

    public ErrorMessages(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public ErrorMessages(String errorMessage) {
        errorMessages.add(errorMessage);
    }

    public void setErrorMessages(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public List<String> getErrorMessages() {

        return errorMessages;
    }
}
