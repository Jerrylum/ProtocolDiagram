package com.jerryio.protocol_diagram.diagram.render.element;

import com.jerryio.protocol_diagram.diagram.Field;
import com.jerryio.protocol_diagram.diagram.render.IVisible;

public class RowTail extends RowSegment implements IVisible {
    private boolean isVisible;

    public boolean isVisible() {
        return isVisible;
    }

    public RowTail(int start, int length, boolean isVisible) {
        super(new Field(null, length), start, length);

        this.isVisible = isVisible;
    }
}
