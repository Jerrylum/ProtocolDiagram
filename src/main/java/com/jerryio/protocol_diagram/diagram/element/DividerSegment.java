package com.jerryio.protocol_diagram.diagram.element;

import com.jerryio.protocol_diagram.diagram.Field;

public class DividerSegment extends Segment {

    public DividerSegment(Field represent, int start, int length) {
        super(represent, start, length);
    }

    @Override
    public String toString() {
        if (getRepresent() == null)
            return "DividerSegment [length=" + getLength() + ", display=" + isDisplayName() + "]";
        else
            return "DividerSegment [name=" + getRepresent().getName() + ", length=" + getLength() + ", display=" + isDisplayName() + "]";
    }
}
