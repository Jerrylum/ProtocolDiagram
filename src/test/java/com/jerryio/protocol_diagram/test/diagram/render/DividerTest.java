package com.jerryio.protocol_diagram.test.diagram.render;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.jerryio.protocol_diagram.diagram.Field;
import com.jerryio.protocol_diagram.diagram.render.Divider;
import com.jerryio.protocol_diagram.diagram.render.element.DividerSegment;
import com.jerryio.protocol_diagram.diagram.render.element.RowSegment;
import com.jerryio.protocol_diagram.diagram.render.element.Segment;

public class DividerTest {
    @Test
    public void testDivider() {
        RowSegment rs = new RowSegment(new Field("test1", 8), 0, 8);
        RowSegment rs2 = new RowSegment(new Field("test2", 6), 0, 6);
        Divider d = new Divider(32);
        d.addSplice(rs2, rs);
        List<Segment> slist = new ArrayList<Segment>();
        slist.add(new DividerSegment(null, 0, 6));
        assertEquals(slist.toString(), d.getSegments().toString());
        d.addSplice(rs2, rs);
        assertEquals(slist.toString(), d.getSegments().toString());


        Field f = new Field("test3", 38);
        rs = new RowSegment(f, 8, 24);
        rs2 = new RowSegment(f, 0, 6);
        d = new Divider(32);
        d.addSplice(rs2, rs);
        slist = new ArrayList<Segment>();
        slist.add(new DividerSegment(null, 0, 6));
        assertEquals(slist.toString(), d.getSegments().toString());

        Field f2 = new Field("test4", 48);
        rs = new RowSegment(f2, 8, 24);
        rs2 = new RowSegment(f2, 0, 24);
        d = new Divider(32);
        d.addSplice(rs2, rs);
        slist = new ArrayList<Segment>();
        slist.add(new DividerSegment(rs2.getRepresent(), 0, 24));
        assertEquals(slist.toString(), d.getSegments().toString());
    }
}
