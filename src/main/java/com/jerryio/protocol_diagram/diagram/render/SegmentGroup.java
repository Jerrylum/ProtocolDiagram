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

    /**
     * a getter method that return the number of bit this group holds
     * 
     * @return int
     */
    public int getBit() {
        return bit;
    }

    /**
     * a method that returns a readonly collection of the segments
     * 
     * @return Collection<T>
     */
    public Collection<T> getSegments() {
        return Collections.unmodifiableCollection(segments);
    }

    /**
     * a method that return the segment by the specified index
     * 
     * @param index
     * @return T extends Segment
     */
    public T get(int index) {
        return segments.get(index);
    }

    /**
     * a getter method that returns the used space for the current segment groups
     * 
     * @return int
     */
    public int getUsed() {
        return used;
    }

    /**
     * a getter method that returns the number of segments of the current segment group owns
     * 
     * @return int
     */
    public int getCount() {
        return segments.size();
    }
}
