package com.jerryio.protocol_diagram.diagram.element;

import com.jerryio.protocol_diagram.diagram.Field;

public class RowTail extends RowSegment {
    public RowTail(int start, int length) {
        super(new Field(null, length), start, length);
    }
}
