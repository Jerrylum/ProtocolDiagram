package com.jerryio.protocol_diagram.diagram;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Row {
    private final int bit;
    private final List<RowSegment> segments;

    private int used;

    public Row(int bit) {
        this.bit = bit;
        this.segments = new ArrayList<RowSegment>();
        this.used = 0;
    }

    public int getBit() {
        return bit;
    }

    public Collection<RowSegment> getSegments() {
        return Collections.unmodifiableCollection(segments);
    }

    public RowSegment getSegment(int index) {
        return segments.get(index);
    }

    public Row addField(int uid, Field field) {
        int consume = Math.min(field.getLength(), bit - used);
        field.setLength(field.getLength() - consume);
        used += consume;

        if (consume != 0)
            segments.add(new RowSegment(uid, field.getName(), consume));

        return this;
    }

    public int getUsed() {
        return used;
    }

    public int[] getSplicePositions() {
        int[] array = new int[segments.size()];
        int index = 0;
        for (int i = 0; i < segments.size(); i++) {
            array[i] = index += segments.get(i).getLength();
        }
        return array;
    }


}
