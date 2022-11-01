package com.jerryio.protocol_diagram.diagram;

import com.jerryio.protocol_diagram.token.Pair;

/**
 * this class comprises the information of the field of the diagram, such as the
 * the name of the field, the length of the field, and the unique identifier of
 * the field.
 */
public class Field {

    private static int uidCount = 0;

    private String name;
    private int length;
    private int uid;

    /**
     * this constructor is used to assign all necessary instance variable at the born of the instance
     * 
     * @param name
     * @param length
     * @param uid
     */
    private Field(String name, int length, int uid) {
        this.name = name;
        this.length = length;
        this.uid = uid;
    }

    /**
     * this constructor is a wrapper constructor that automatically increase the static `uidCount`
     * and pass the instantiation logic to the first constructor
     * 
     * @param name
     * @param length
     */
    public Field(String name, int length) {
        this(name, length, uidCount++);
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

    public int getUid() {
        return uid;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Field) {
            Field field = (Field) obj;
            return field.getUid() == getUid();
        }

        return false;
    }

    public Field clone() {
        return new Field(name, length, uid);
    }

}
