package com.jerryio.protocol_diagram.test.diagram.render;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.jerryio.protocol_diagram.diagram.Field;
import com.jerryio.protocol_diagram.diagram.render.SegmentGroup;
import com.jerryio.protocol_diagram.diagram.render.element.DividerSegment;
import com.jerryio.protocol_diagram.diagram.render.element.RowSegment;
import com.jerryio.protocol_diagram.diagram.render.element.RowTail;
import com.jerryio.protocol_diagram.diagram.render.element.Segment;

public class SegmentGroupTest {

    @Test
    public void testSegmentGroup() {
        class SegmentGroupTestStub extends SegmentGroup<Segment> {
            public SegmentGroupTestStub(int bit) {
                super(bit);
            }
            public void add(Segment s) {
                segments.add(s);
                used += s.getLength();
            }
        }
        SegmentGroupTestStub sg = new SegmentGroupTestStub(32);
        assertEquals(0, sg.getUsed());
        assertEquals(32, sg.getBit());
        RowSegment rs = new RowSegment(new Field("test1", 8), 0, 8);
        RowTail rt = new RowTail(8, 24, true);
        DividerSegment ds = new DividerSegment(new Field("test2", 8), 0, 8);
        List<Segment> segments = new ArrayList<Segment>();


        segments.add(rs);
        sg.add(rs);
        assertEquals(8, sg.getUsed());
        assertEquals(segments.toString(), sg.getSegments().toString());
        assertEquals(rs, sg.get(0));
        assertEquals(1, sg.getCount());

        segments.add(rt);
        sg.add(rt);
        assertEquals(32, sg.getUsed());
        assertEquals(segments.toString(), sg.getSegments().toString());
        assertEquals(rt, sg.get(1));
        assertEquals(2, sg.getCount());

        segments.add(ds);
        sg.add(ds);
        assertEquals(40, sg.getUsed());
        assertEquals(segments.toString(), sg.getSegments().toString());
        assertEquals(ds, sg.get(2));
        assertEquals(3, sg.getCount());
    }
    
}
