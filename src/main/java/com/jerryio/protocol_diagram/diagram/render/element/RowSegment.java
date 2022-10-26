package com.jerryio.protocol_diagram.diagram.render.element;

import com.jerryio.protocol_diagram.diagram.Field;
import com.jerryio.protocol_diagram.diagram.render.Matrix;

public class RowSegment extends Segment {

    public RowSegment(Field represent, int start, int length) {
        super(represent, start, length);
    }

    @Override
    public void process(Matrix m, int x, int y) {
        // Nothing to do
    }

    @Override
    public String toString() {
        return "RowSegment [name=" + getRepresent().getName() + ", start=" + getStartIndex() + ", end=" + getEndIndex() + ", display=" + isDisplayName() + "]";
    }
}
