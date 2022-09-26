package com.jerryio.protocol_diagram.diagram;

import com.jerryio.protocol_diagram.token.Pair;

public class Field {

    private String name;
    private int length;

    public Field(String name, int length) {
        this.name = name;
        this.length = length;
    }

    public Field(Pair<String, Integer> pair) {
        this(pair.first(), pair.second());
    }

    public String getName() {
        return name;
    }

    public int getLength() {
        return length;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLength(int length) {
        this.length = length;
    }

}
