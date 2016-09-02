package com.boiler_plate.mobile.managers;

import java.util.LinkedList;
/**
 * Dummy manager that is utilized in the MVVM architecture proof of concept
 *
 * @author      Juan Garcia
 * @created 	2016-01-26
 */
public class DummyManager {
    /**
     * This is a dummy Method that returns random strings
     *
     * @author Juan Garcia
     * @return A random String
     */
    public String dummyString() {
        LinkedList<String> myStrings = new LinkedList<>();
        myStrings.add("This");
        myStrings.add("is");
        myStrings.add("the");
        myStrings.add("TC");
        myStrings.add("MVVM");
        myStrings.add("Demo");
        int randomNum = 0 + (int)(Math.random() * myStrings.size());
        return myStrings.get(randomNum);
    }
}
