package com.jerryio.protocol_diagram.test.diagram.render;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.jerryio.protocol_diagram.diagram.Field;
import com.jerryio.protocol_diagram.diagram.render.Divider;
import com.jerryio.protocol_diagram.diagram.render.Matrix;
import com.jerryio.protocol_diagram.diagram.render.Row;
import com.jerryio.protocol_diagram.diagram.render.element.Connector;
import com.jerryio.protocol_diagram.diagram.render.element.DividerSegment;
import com.jerryio.protocol_diagram.diagram.render.element.NextLine;
import com.jerryio.protocol_diagram.diagram.render.element.RowSegment;
import com.jerryio.protocol_diagram.diagram.render.element.RowTail;
import com.jerryio.protocol_diagram.diagram.render.element.Segment;
import com.jerryio.protocol_diagram.util.DiagramUtils;

public class MatrixTest {
    @Test
    public void testMatrixConstructor() {
        new Matrix(new ArrayList<Segment>());

        int i = 0;
        List<Field> flist = new ArrayList<>();
        flist.add(new Field("test1", 8));
        i += 2;
        for (int j = 1; j < 8; j++) {
            i += 2;
        }
        flist.add(new Field("test2", 48));
        i += 2;
        for (int j = 1; j < 48; j++) {
            i += 2;
        }
        flist.add(new Field("test3", 8));
        i += 2;
        for (int j = 1; j < 8; j++) {
            i += 2;
        }
        List<Row> rows = DiagramUtils.convertFieldsToRow(32, flist, true);
        assertEquals(2, rows.size());
        List<Divider> dividers = DiagramUtils.spliceDividers(32, rows);
        assertEquals(3, dividers.size());
        assertEquals(2, dividers.get(0).getSegments().size());
        for (int j = 0; j < dividers.get(0).getSegments().size(); j++) {
            i += 2;
            for (int k = 1; k < dividers.get(0).get(j).getLength(); k++) {
                i += 2;
            }
        }
        assertEquals(3, dividers.get(1).getSegments().size());
        for (int j = 0; j < dividers.get(1).getSegments().size(); j++) {
            i += 2;
            for (int k = 1; k < dividers.get(1).get(j).getLength(); k++) {
                i += 2;
            }
        }
        assertEquals(2, dividers.get(2).getSegments().size());
        for (int j = 0; j < dividers.get(2).getSegments().size(); j++) {
            i += 2;
            for (int k = 1; k < dividers.get(2).get(j).getLength(); k++) {
                i += 2;
            }
        }
        List<Segment> segments = DiagramUtils.mergeRowsAndDividers(rows, dividers);
        assertEquals(4 + 2 + 3 + 2, segments.size());
        DiagramUtils.setDisplayNameForAllFields(segments, flist);
        Matrix matrix = new Matrix(segments);
        assertEquals(i + 10, matrix.getElements().size());
        assertEquals(5, matrix.getHeight());
        assertEquals((i + 10) / 5, matrix.getWidth());
    }

    @Test
    public void testGet() {
        List<Field> flist = new ArrayList<>();
        flist.add(new Field("test1", 8));
        flist.add(new Field("test2", 48));
        List<Row> rows = DiagramUtils.convertFieldsToRow(32, flist, true);
        List<Divider> dividers = DiagramUtils.spliceDividers(32, rows);
        List<Segment> segments = DiagramUtils.mergeRowsAndDividers(rows, dividers);
        DiagramUtils.setDisplayNameForAllFields(segments, flist);
        Matrix matrix = new Matrix(segments);
        assertNull(matrix.get(-1, -1));
        assertNull(matrix.get(0, -1));
        assertNull(matrix.get(-1, 0));
        assertNull(matrix.get(matrix.getWidth(), 0));
        assertNull(matrix.get(0, matrix.getHeight()));

        assertTrue(matrix.get(0, 0) instanceof Connector);
        assertTrue(matrix.get(1, 0) instanceof DividerSegment);
        assertTrue(matrix.get(matrix.getWidth() - 1, 0) instanceof NextLine);
        assertTrue(matrix.get(0, 1) instanceof Connector);
        assertTrue(matrix.get(1, 1) instanceof RowSegment);
        int i = 0;
        i += 2;
        for (int j = 1; j < 8; j++) {
            i += 2;
        }
        assertTrue(matrix.get(i, 1) instanceof Connector);
        assertTrue(matrix.get(matrix.getWidth() - 3, matrix.getHeight() - 2) instanceof RowTail);
    }

    @Test
    public void testConnectorProcess() {
        List<Field> flist = new ArrayList<>();
        flist.add(new Field("test1", 8));
        flist.add(new Field("test2", 48));
        List<Row> rows = DiagramUtils.convertFieldsToRow(32, flist, true);
        List<Divider> dividers = DiagramUtils.spliceDividers(32, rows);
        List<Segment> segments = DiagramUtils.mergeRowsAndDividers(rows, dividers);
        DiagramUtils.setDisplayNameForAllFields(segments, flist);
        Matrix matrix = new Matrix(segments);
        matrix.process();
        matrix.process();
        assertTrue(matrix.get(matrix.getWidth()-2, 2) instanceof Connector);
        assertEquals(Connector.TOP+Connector.LEFT, ((Connector)matrix.get(matrix.getWidth()-2, 2)).getValue());
        assertTrue(matrix.get(matrix.getWidth()-2, 3) instanceof Connector);
        assertEquals(Connector.TOP+Connector.LEFT+Connector.BOTTOM, ((Connector)matrix.get(matrix.getWidth()-2, 3)).getValue());
        assertTrue(((Connector)matrix.get(matrix.getWidth()-2, 3)).isIndividual());
        assertTrue(matrix.get(matrix.getWidth()-2, 4) instanceof Connector);
        assertEquals(0, ((Connector)matrix.get(matrix.getWidth()-2, 4)).getValue());
    }

    @Test
    public void testDividerSegmentProcess() {
        List<Field> flist = new ArrayList<>();
        flist.add(new Field("test1", 8));
        flist.add(new Field("test2", 48));
        List<Row> rows = DiagramUtils.convertFieldsToRow(32, flist, true);
        List<Divider> dividers = DiagramUtils.spliceDividers(32, rows);
        List<Segment> segments = DiagramUtils.mergeRowsAndDividers(rows, dividers);
        DiagramUtils.setDisplayNameForAllFields(segments, flist);
        Matrix matrix = new Matrix(segments);
        matrix.process();
        matrix.process();
        assertTrue(matrix.get(matrix.getWidth()-20, 2) instanceof DividerSegment);
        assertFalse(((DividerSegment)matrix.get(matrix.getWidth()-20, 2)).isVisible());
        assertTrue(((DividerSegment)matrix.get(matrix.getWidth()-20, 0)).isVisible());
        assertTrue(((DividerSegment)matrix.get(matrix.getWidth()-20, 4)).isVisible());
        assertTrue(matrix.get(matrix.getWidth()-3, 4) instanceof DividerSegment);
        assertFalse(((DividerSegment)matrix.get(matrix.getWidth()-3, 4)).isVisible());
    }
}
