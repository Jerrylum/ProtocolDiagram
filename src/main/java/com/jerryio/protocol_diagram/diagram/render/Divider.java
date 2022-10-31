package com.jerryio.protocol_diagram.diagram.render;

import com.jerryio.protocol_diagram.diagram.Field;
import com.jerryio.protocol_diagram.diagram.render.element.DividerSegment;
import com.jerryio.protocol_diagram.diagram.render.element.RowSegment;

public class Divider extends SegmentGroup<DividerSegment> {

    public Divider(int bit) {
        super(bit);
    }

    /**
     * a method for appending a splice into the segment group (divider),
     * note that the endindex of before should be always less than the
     * endindex of after
     * 
     * @param before
     * @param after
     * @return void
     */
    public void addSplice(RowSegment before, RowSegment after) {
        int length = before.getEndIndex() - used;
        if (length != 0) {
            Field represent = null;
            if (before.getRepresent().equals(after.getRepresent()) && before.getEndIndex() > after.getStartIndex())
                represent = before.getRepresent();
            segments.add(new DividerSegment(represent, used, length));
            used += length;
        }
    }
}
