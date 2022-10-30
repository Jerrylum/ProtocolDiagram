package com.jerryio.protocol_diagram.diagram;

import com.jerryio.protocol_diagram.token.Pair;

public class Field {

    private String name;
    private int length;

    public Field(String name, int length) {
        this.name = name;
        this.length = length;
    }

    /**
     * this constructor will transform a pair of value into then field name and the
     * length of the field
     * 
     * @param pair
     */
    public Field(Pair<String, Integer> pair) {
        this(pair.first(), pair.second());
    }

    /**
     * a getter method that returns the name of this diagram field
     * 
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * a getter method that returns the length of this diagram field
     */
    public int getLength() {
        return length;
    }

    /**
     * a setter method that sets the name of this diagram field
     * 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * a setter method that sets the length of this diagram field
     * 
     * @param length
     */
    public void setLength(int length) {
        this.length = length;
    }

}
