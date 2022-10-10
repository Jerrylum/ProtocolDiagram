package com.jerryio.protocol_diagram.diagram;

import java.util.ArrayList;
import java.util.Collection;
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
        return segments;
    }

    // public void addFloorSegment(FloorSegment f) {
    //     segments.add(f);
    //     used += f.getLength();
    // }

    public void addSplice(int uid, int position) {
        int length = position - used;
        if (length != 0) {
            segments.add(new FloorSegment(uid, length));
            used += length;
        }
    }

    public int getUsed() {
        return used;
    }
}
