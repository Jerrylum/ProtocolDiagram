package com.jerryio.protocol_diagram.diagram;

import com.jerryio.protocol_diagram.token.Pair;

public class Field {

    private static int uidCount = 0;

    private String name;
    private int length;
    private int uid;

    public Field(String name, int length) {
        this.name = name;
        this.length = length;
        this.uid = uidCount++;
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

}
