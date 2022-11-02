package com.jerryio.protocol_diagram.test.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.jerryio.protocol_diagram.diagram.Field;
import com.jerryio.protocol_diagram.diagram.render.Divider;
import com.jerryio.protocol_diagram.diagram.render.IVisible;
import com.jerryio.protocol_diagram.diagram.render.Matrix;
import com.jerryio.protocol_diagram.diagram.render.Row;
import com.jerryio.protocol_diagram.diagram.render.element.DividerSegment;
import com.jerryio.protocol_diagram.diagram.render.element.Element;
import com.jerryio.protocol_diagram.diagram.render.element.RowSegment;
import com.jerryio.protocol_diagram.diagram.render.element.RowTail;
import com.jerryio.protocol_diagram.diagram.render.element.Segment;
import com.jerryio.protocol_diagram.util.DiagramUtils;
import com.jerryio.protocol_diagram.util.DiagramUtils.SpliceDividerService;

public class DiagramUtilsTest {
    @Test
    public void testDiagramUtil() {
        new DiagramUtils(); //dummy

        List<Row> temp = DiagramUtils.convertFieldsToRow(32, new ArrayList<Field>(), false);
        assertEquals(0, temp.size());

        temp = DiagramUtils.convertFieldsToRow(32, new ArrayList<Field>(){{
            add(new Field("test", 8));
        }}, false);
        assertEquals(1, temp.size());
        assertEquals(32, temp.get(0).getBit());
        assertEquals(32, temp.get(0).getUsed());

        temp = DiagramUtils.convertFieldsToRow(32, new ArrayList<Field>(){{
            add(new Field("test", 8));
            add(new Field("test", 8));
            add(new Field("test", 24));
        }}, false);
        assertEquals(2, temp.size());
        assertEquals(32, temp.get(0).getBit());
        assertEquals(32, temp.get(0).getUsed());
        assertEquals(3, temp.get(0).getSegments().size());
        assertTrue(temp.get(0).get(2) instanceof RowSegment);
        assertEquals(32, temp.get(1).getBit());
        assertEquals(32, temp.get(1).getUsed());
        assertEquals(2, temp.get(1).getSegments().size());
        assertTrue(temp.get(1).get(0) instanceof RowSegment);
        assertTrue(temp.get(1).get(1) instanceof RowTail);
    }

    @Test
    public void testgenerateHeader() {
        class IVisibleTestStub extends Element implements IVisible {
            private boolean isVisible = false;
            public IVisibleTestStub(Boolean isVisible) {
                super();
                this.isVisible = isVisible;
            }
            @Override
            public boolean isVisible() {
                return isVisible;
            }

            @Override
            public void process(Matrix m, int x, int y) {
                // TODO Auto-generated method stub
                
            }
        }
        class SegmentTestStub extends Segment {
            public SegmentTestStub(Field represent, int start, int length) {
                super(represent, start, length);
            }

            @Override
            public void process(Matrix m, int x, int y) {
                // TODO Auto-generated method stub
                
            }
        }

        List<Element> elist = new ArrayList<>();
        IVisibleTestStub ivt = new IVisibleTestStub(true);
        SegmentTestStub st = new SegmentTestStub(new Field("test", 8), 0, 8);
        elist.add(ivt);
        elist.add(st);
        assertEquals("", DiagramUtils.generateHeader(elist, 32, "UTF8"));
        DividerSegment rs = new DividerSegment(new Field("test", 8), 0, 8);
        elist.add(rs);
        assertEquals("", DiagramUtils.generateHeader(elist, 32, "none"));
    }

    @Test
    public void testSpliceDividerService() {
        Row r1 = new Row(32);
        Row r2 = new Row(32);
        r1.addField(new Field("test1", 8));
        r1.addField(new Field("test2", 24));
        r2.addField(new Field("test3", 24));
        r2.addField(new Field("test4", 8));
        SpliceDividerService sds = new SpliceDividerService(32, new ArrayList<Row>(){{
            add(r1);
            add(r2);
        }});
        assertEquals(4, sds.rows.size());
        assertEquals(r1, sds.rows.get(1));
        assertEquals(r2, sds.rows.get(2));

        Divider d = new Divider(32);
        d.addSplice(r1.get(0), r2.get(0));
        d.addSplice(r2.get(0), r1.get(1));
        d.addSplice(r1.get(1), r2.get(1));
        List<Divider> dlist = sds.splice();
        assertEquals(3, dlist.size());
        assertEquals(3, dlist.get(1).getSegments().size());
        assertEquals(d.get(0).toString(), dlist.get(1).get(0).toString());
        assertEquals(d.get(1).toString(), dlist.get(1).get(1).toString());
        assertEquals(d.get(2).toString(), dlist.get(1).get(2).toString());

        Row r3 = new Row(32);
        Row r4 = new Row(32);
        r3.addField(new Field("test1", 24));
        r3.addField(new Field("test2", 8));
        r4.addField(new Field("test3", 8));
        r4.addField(new Field("test4", 24));
        sds = new SpliceDividerService(32, new ArrayList<Row>(){{
            add(r3);
            add(r4);
        }});
        assertEquals(4, sds.rows.size());
        assertEquals(r3, sds.rows.get(1));
        assertEquals(r4, sds.rows.get(2));
        
        d = new Divider(32);
        d.addSplice(r4.get(0), r3.get(0));
        d.addSplice(r3.get(0), r4.get(1));
        d.addSplice(r3.get(1), r4.get(1));
        dlist = sds.splice();
        assertEquals(3, dlist.size());
        assertEquals(3, dlist.get(1).getSegments().size());
        assertEquals(d.get(0).toString(), dlist.get(1).get(0).toString());
        assertEquals(d.get(1).toString(), dlist.get(1).get(1).toString());
        assertEquals(d.get(2).toString(), dlist.get(1).get(2).toString());

        Row r5 = new Row(32);
        Row r6 = new Row(32);
        r5.addField(new Field("test1", 16));
        r5.addField(new Field("test2", 16));
        r6.addField(new Field("test3", 16));
        r6.addField(new Field("test4", 16));
        sds = new SpliceDividerService(32, new ArrayList<Row>(){{
            add(r5);
            add(r6);
        }});
        assertEquals(4, sds.rows.size());
        assertEquals(r5, sds.rows.get(1));
        assertEquals(r6, sds.rows.get(2));

        d = new Divider(32);
        d.addSplice(r5.get(0), r6.get(0));
        d.addSplice(r5.get(1), r6.get(1));
        dlist = sds.splice();
        assertEquals(3, dlist.size());
        assertEquals(2, dlist.get(1).getSegments().size());
        assertEquals(d.get(0).toString(), dlist.get(1).get(0).toString());
        assertEquals(d.get(1).toString(), dlist.get(1).get(1).toString());

        Row r7 = new Row(32);
        Row r8 = new Row(32);
        r7.addField(new Field("test1", 8));
        r7.addField(new Field("test2", 24));
        r8.addField(new Field("test3", 32));
        sds = new SpliceDividerService(32, new ArrayList<Row>(){{
            add(r7);
            add(r8);
        }});
        assertEquals(4, sds.rows.size());
        assertEquals(r7, sds.rows.get(1));
        assertEquals(r8, sds.rows.get(2));

        d = new Divider(32);
        d.addSplice(r7.get(0), r8.get(0));
        d.addSplice(r7.get(1), r8.get(0));
        dlist = sds.splice();
        assertEquals(3, dlist.size());
        assertEquals(2, dlist.get(1).getSegments().size());
        assertEquals(d.get(0).toString(), dlist.get(1).get(0).toString());
        assertEquals(d.get(1).toString(), dlist.get(1).get(1).toString());

        Row r9 = new Row(32);
        r9.addField(new Field("test1", 32));
        sds = new SpliceDividerService(32, new ArrayList<Row>(){{
            add(r9);
        }});
        assertEquals(3, sds.rows.size());
        assertEquals(r9, sds.rows.get(1));
        Row r10 =  new Row(32).addField(new Field(null, 32));
        d = new Divider(32);
        d.addSplice(r9.get(0), r10.get(0));
        dlist = sds.splice();
        assertEquals(2, dlist.size());
        assertEquals(1, dlist.get(1).getSegments().size());
        assertEquals(d.get(0).toString(), dlist.get(1).get(0).toString());


        Row r11 = new Row(32);
        Row r12 = new Row(32);
        r11.addField(new Field("test1", 32));
        r12.addField(new Field("test2", 24));
        r12.addField(new Field("test3", 8));
        sds = new SpliceDividerService(32, new ArrayList<Row>(){{
            add(r11);
            add(r12);
        }});
        assertEquals(4, sds.rows.size());
        assertEquals(r11, sds.rows.get(1));
        assertEquals(r12, sds.rows.get(2));

        d = new Divider(32);
        d.addSplice(r12.get(0), r11.get(0));
        d.addSplice(r11.get(0), r12.get(1));
        dlist = sds.splice();
        assertEquals(3, dlist.size());
        assertEquals(2, dlist.get(1).getSegments().size());
        assertEquals(d.get(0).toString(), dlist.get(1).get(0).toString());
        assertEquals(d.get(1).toString(), dlist.get(1).get(1).toString());

        Row r13 = new Row(32);
        Row r14 = new Row(32);
        r13.addField(new Field("test1", 32));
        r14.addField(new Field("test2", 32));
        sds = new SpliceDividerService(32, new ArrayList<Row>(){{
            add(r13);
            add(r14);
        }});
        assertEquals(4, sds.rows.size());
        assertEquals(r13, sds.rows.get(1));
        assertEquals(r14, sds.rows.get(2));

        d = new Divider(32);
        d.addSplice(r13.get(0), r14.get(0));
        dlist = sds.splice();
        assertEquals(3, dlist.size());
        assertEquals(1, dlist.get(1).getSegments().size());
        assertEquals(d.get(0).toString(), dlist.get(1).get(0).toString());
    }
}
