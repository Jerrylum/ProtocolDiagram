package com.jerryio.protocol_diagram.diagram;

public class RowSegment {
    private int uid;
    private String name;
    private int length;

    public RowSegment(int uid, String name, int length) {
        this.uid = uid;
        this.name = name;
        this.length = length;
    }

    public int getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public int getLength() {
        return length;
    }

    @Override
    public String toString() {
        return "RowSegment [uid=" + uid + ", name=" + name + ", length=" + length + "]";
    }
}
