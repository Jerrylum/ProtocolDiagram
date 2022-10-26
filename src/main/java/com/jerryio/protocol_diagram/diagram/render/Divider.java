package com.jerryio.protocol_diagram.diagram.render;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.jerryio.protocol_diagram.diagram.Field;
import com.jerryio.protocol_diagram.diagram.render.element.DividerSegment;
import com.jerryio.protocol_diagram.diagram.render.element.RowSegment;

public class Divider {
    private final int bit;
    private final List<DividerSegment> segments;

    private int used;

    public Divider(int bit) {
        this.bit = bit;
        this.segments = new ArrayList<DividerSegment>();
        this.used = 0;
    }

    public int getBit() {
        return bit;
    }

    public Collection<DividerSegment> getSegments() {
        return Collections.unmodifiableCollection(segments);
    }

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

    public int getUsed() {
        return used;
    }
}
