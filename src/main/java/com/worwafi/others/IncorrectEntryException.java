package com.worwafi.others;
import javafx.beans.property.StringProperty;

public class IncorrectEntryException extends Exception {
    /**
     * Incorrect entry of numbers in the creation of object, changes the text in the textfield
     * @param field field where the exception happened
     * @param stringProperty field to change the text at
     */
    public IncorrectEntryException(String field, StringProperty stringProperty) {
        super("Incorrect price on " + field + ", only numbers allowed");
        stringProperty.setValue("Incorrect price on " + field + ", only numbers allowed");
    }
}
