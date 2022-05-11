package com.worwafi.others;
import javafx.beans.property.StringProperty;

public class IncorrectEntryException extends Exception {
    public IncorrectEntryException(String field, StringProperty stringProperty) {
        super("Incorrect price on " + field + ", only numbers allowed");
        stringProperty.setValue("Incorrect price on " + field + ", only numbers allowed");
    }
}
