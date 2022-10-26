package com.jerryio.protocol_diagram.diagram.element;

import com.jerryio.protocol_diagram.diagram.Field;

public class DividerSegment extends Segment {
    private boolean isVisible = true;

    public DividerSegment(Field represent, int start, int length) {
        super(represent, start, length);
    }

    public boolean isVisible() {
        return isVisible;
    }

    @Override
    public void process(Matrix m, int x, int y) {
        Element up = m.get(x, y - 1);
        if (up == null || up instanceof RowTail)
            isVisible = false;
    }

    @Override
    public String toString() {
        if (getRepresent() == null)
            return "DividerSegment [length=" + getLength() + ", display=" + isDisplayName() + "]";
        else
            return "DividerSegment [name=" + getRepresent().getName() + ", length=" + getLength() + ", display=" + isDisplayName() + "]";
    }

}
