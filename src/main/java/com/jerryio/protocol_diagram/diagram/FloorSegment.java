package com.jerryio.protocol_diagram.diagram;

public class FloorSegment {
    private int uid;
    private int length;

    public FloorSegment(int uid, int length) {
        this.uid = uid;
        this.length = length;
    }

    public int getUid() {
        return uid;
    }

    public int getLength() {
        return length;
    }

    @Override
    public String toString() {
        return "FloorSegment [uid=" + uid + ", length=" + length + "]";
    }
}
