package com.jerryio.protocol_diagram.diagram.render;

import com.jerryio.protocol_diagram.diagram.Field;
import com.jerryio.protocol_diagram.diagram.render.element.RowSegment;
import com.jerryio.protocol_diagram.diagram.render.element.RowTail;

public class Row extends SegmentGroup<RowSegment> {

    public Row(int bit) {
        super(bit);
    }

    public Row addField(Field field) {
        int consume = Math.min(field.getLength(), bit - used);
        field.setLength(field.getLength() - consume);

        if (consume != 0)
            segments.add(new RowSegment(field, used, consume));
        used += consume;

        return this;
    }

    public void addTail(boolean isVisible) {
        if (used != bit)
            segments.add(new RowTail(used, bit - used, isVisible));
        used = bit;
    }
}
