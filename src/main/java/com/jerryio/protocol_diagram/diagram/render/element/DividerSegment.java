package com.jerryio.protocol_diagram.diagram.render.element;

import com.jerryio.protocol_diagram.diagram.Field;
import com.jerryio.protocol_diagram.diagram.render.IVisible;
import com.jerryio.protocol_diagram.diagram.render.Matrix;

public class DividerSegment extends Segment implements IVisible {
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
        Element down = m.get(x, y + 1);
        boolean isUp = up != null && !(up instanceof RowTail);
        boolean isDown = down != null && !(down instanceof RowTail);
        isVisible = (isUp || isDown) && getRepresent() == null;
    }

    @Override
    public String toString() {
        if (getRepresent() == null)
            return "DividerSegment [length=" + getLength() + ", display=" + isDisplayName() + "]";
        else
            return "DividerSegment [name=" + getRepresent().getName() + ", length=" + getLength() + ", display="
                    + isDisplayName() + "]";
    }

}
