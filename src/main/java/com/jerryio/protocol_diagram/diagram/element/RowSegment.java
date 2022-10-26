package com.jerryio.protocol_diagram.diagram.element;

import com.jerryio.protocol_diagram.diagram.Field;

public class RowSegment extends Segment {

    public RowSegment(Field represent, int start, int length) {
        super(represent, start, length);
    }

    @Override
    public String toString() {
        return "RowSegment [name=" + getRepresent().getName() + ", start=" + getStartIndex() + ", end=" + getEndIndex() + ", display=" + isDisplayName() + "]";
    }
}
