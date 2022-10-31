package com.jerryio.protocol_diagram.diagram.render;

import com.jerryio.protocol_diagram.diagram.Field;
import com.jerryio.protocol_diagram.diagram.render.element.RowSegment;
import com.jerryio.protocol_diagram.diagram.render.element.RowTail;

public class Row extends SegmentGroup<RowSegment> {

    public Row(int bit) {
        super(bit);
    }

    /**
     * a method for appending a field into the segment group (row)
     * 
     * @param field
     * @return
     */
    public Row addField(Field field) {
        int consume = Math.min(field.getLength(), bit - used);
        field.setLength(field.getLength() - consume);

        if (consume != 0)
            segments.add(new RowSegment(field, used, consume));
        used += consume;

        return this;
    }

    /**
     * a method for appending row tail also know as the reserved slot into
     * the segment group, note that if the used space is equal to the number
     * of bit it has, no row tail would be appended has the row is already full
     * 
     * @param isVisible
     */
    public void addTail(boolean isVisible) {
        if (used != bit)
            segments.add(new RowTail(used, bit - used, isVisible));
        used = bit;
    }
}
