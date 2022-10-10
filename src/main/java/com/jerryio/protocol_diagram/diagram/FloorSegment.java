package com.jerryio.protocol_diagram.diagram;

public class FloorSegment extends Segment {

    public FloorSegment(Field represent, int start, int length) {
        super(represent, start, length);
    }

    @Override
    public String toString() {
        if (getRepresent() == null)
            return "FloorSegment [length=" + getLength() + ", display=" + isDisplayName() + "]";
        else
            return "FloorSegment [name=" + getRepresent().getName() + ", length=" + getLength() + ", display=" + isDisplayName() + "]";
    }
}
