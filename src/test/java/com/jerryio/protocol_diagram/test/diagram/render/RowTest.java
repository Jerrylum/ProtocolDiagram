package com.jerryio.protocol_diagram.test.diagram.render;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.jerryio.protocol_diagram.diagram.Field;
import com.jerryio.protocol_diagram.diagram.render.Row;
import com.jerryio.protocol_diagram.diagram.render.element.RowSegment;
import com.jerryio.protocol_diagram.diagram.render.element.RowTail;
import com.jerryio.protocol_diagram.diagram.render.element.Segment;

public class RowTest {
    @Test
    public void testRow() {
        Row r = new Row(32);
        List<Segment> slist = new ArrayList<Segment>();
        assertEquals(0, r.getUsed());
        assertEquals(32, r.getBit());
        assertEquals(slist.toString(), r.getSegments().toString());

        r.addField(new Field("test1", 8));
        slist.add(new RowSegment(new Field("test1", 0), 0, 8));
        assertEquals(8, r.getUsed());
        assertEquals(slist.toString(), r.getSegments().toString());

        r.addField(new Field("test2", 8));
        slist.add(new RowSegment(new Field("test2", 0), 8, 8));
        assertEquals(16, r.getUsed());
        assertEquals(slist.toString(), r.getSegments().toString());

        r.addField(new Field("test3", 24));
        slist.add(new RowSegment(new Field("test3", 8), 16, 16));
        assertEquals(32, r.getUsed());
        assertEquals(slist.toString(), r.getSegments().toString());

        r.addField(new Field("test4", 8));
        assertEquals(32, r.getUsed());
        assertEquals(slist.toString(), r.getSegments().toString());

        r.addTail(true);
        assertEquals(slist.toString(), r.getSegments().toString());

        r = new Row(32);
        slist.clear();

        r.addField(new Field("test1", 8));
        slist.add(new RowSegment(new Field("test1", 0), 0, 8));
        assertEquals(8, r.getUsed());
        assertEquals(slist.toString(), r.getSegments().toString());
        r.addTail(false);
        slist.add(new RowTail(8, 24, false));
        assertEquals(slist.toString(), r.getSegments().toString());
    }
}
