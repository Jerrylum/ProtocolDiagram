package com.jerryio.protocol_diagram.diagram;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Floor {
    private final int bit;
    private final List<FloorSegment> segments;

    private int used;

    public Floor(int bit) {
        this.bit = bit;
        this.segments = new ArrayList<FloorSegment>();
        this.used = 0;
    }

    public int getBit() {
        return bit;
    }

    public Collection<FloorSegment> getSegments() {
        return Collections.unmodifiableCollection(segments);
    }

    public void addSplice(RowSegment before, RowSegment after) {
        int length = before.getEndIndex() - used;
        if (length != 0) {
            Field represent = null;
            if (before.getRepresent().equals(after.getRepresent()) && before.getEndIndex() > after.getStartIndex())
                represent = before.getRepresent();
            segments.add(new FloorSegment(represent, used, length));
            used += length;
        }
    }

    public int getUsed() {
        return used;
    }
}
