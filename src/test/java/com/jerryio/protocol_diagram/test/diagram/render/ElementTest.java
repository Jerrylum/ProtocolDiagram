package com.jerryio.protocol_diagram.test.diagram.render;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.jerryio.protocol_diagram.diagram.Field;
import com.jerryio.protocol_diagram.diagram.render.Divider;
import com.jerryio.protocol_diagram.diagram.render.Matrix;
import com.jerryio.protocol_diagram.diagram.render.Row;
import com.jerryio.protocol_diagram.diagram.render.element.Connector;
import com.jerryio.protocol_diagram.diagram.render.element.DividerSegment;
import com.jerryio.protocol_diagram.diagram.render.element.Segment;
import com.jerryio.protocol_diagram.util.DiagramUtils;

public class ElementTest {

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
        assertTrue(matrix.get(matrix.getWidth() - 2, 2) instanceof Connector);
        assertEquals(Connector.TOP + Connector.LEFT, ((Connector) matrix.get(matrix.getWidth() - 2, 2)).getValue());
        assertTrue(matrix.get(matrix.getWidth() - 2, 3) instanceof Connector);
        assertEquals(Connector.TOP + Connector.LEFT + Connector.BOTTOM,
                ((Connector) matrix.get(matrix.getWidth() - 2, 3)).getValue());
        assertTrue(((Connector) matrix.get(matrix.getWidth() - 2, 3)).isIndividual());
        assertTrue(matrix.get(matrix.getWidth() - 2, 4) instanceof Connector);
        assertEquals(0, ((Connector) matrix.get(matrix.getWidth() - 2, 4)).getValue());
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
        assertTrue(matrix.get(matrix.getWidth() - 20, 2) instanceof DividerSegment);
        assertFalse(((DividerSegment) matrix.get(matrix.getWidth() - 20, 2)).isVisible());
        assertTrue(((DividerSegment) matrix.get(matrix.getWidth() - 20, 0)).isVisible());
        assertTrue(((DividerSegment) matrix.get(matrix.getWidth() - 20, 4)).isVisible());
        assertTrue(matrix.get(matrix.getWidth() - 3, 4) instanceof DividerSegment);
        assertFalse(((DividerSegment) matrix.get(matrix.getWidth() - 3, 4)).isVisible());
    }
}
