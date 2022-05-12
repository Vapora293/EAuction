package com.worwafi.others;

public abstract class Starter implements HelpMethods {
    /**
     * Class from which other objects inherit because of generic class implementation
     * @param actual thing to compare
     * @return whether object parameters are the same
     */
    public abstract boolean compare(Starter actual);
}
