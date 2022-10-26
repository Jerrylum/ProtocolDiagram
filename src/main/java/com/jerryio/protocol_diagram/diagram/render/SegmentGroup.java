package com.jerryio.protocol_diagram.diagram.render;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.jerryio.protocol_diagram.diagram.render.element.Segment;

public class SegmentGroup<T extends Segment> {
    protected final int bit;
    protected final List<T> segments;
    protected int used;

    public SegmentGroup(int bit) {
        this.bit = bit;
        this.segments = new ArrayList<T>();
        this.used = 0;
    }

    public int getBit() {
        return bit;
    }

    public Collection<T> getSegments() {
        return Collections.unmodifiableCollection(segments);
    }

    public T get(int index) {
        return segments.get(index);
    }

    public int getUsed() {
        return used;
    }

    public int getCount() {
        return segments.size();
    }
}
