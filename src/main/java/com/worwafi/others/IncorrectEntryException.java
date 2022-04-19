package com.worwafi.others;

import javafx.beans.property.StringProperty;

public class IncorrectEntryException extends NumberFormatException {
    public IncorrectEntryException(String errorMessage, StringProperty stringProperty) {
        super(errorMessage);
        stringProperty.setValue(errorMessage);
    }
}
