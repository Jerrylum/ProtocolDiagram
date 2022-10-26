package com.jerryio.protocol_diagram.util;

import java.util.ArrayList;
import java.util.List;

import com.jerryio.protocol_diagram.diagram.Field;
import com.jerryio.protocol_diagram.diagram.render.Divider;
import com.jerryio.protocol_diagram.diagram.render.IVisible;
import com.jerryio.protocol_diagram.diagram.render.Row;
import com.jerryio.protocol_diagram.diagram.render.element.Element;
import com.jerryio.protocol_diagram.diagram.render.element.RowSegment;
import com.jerryio.protocol_diagram.diagram.render.element.Segment;

public class DiagramUtils {

    public static List<Row> convertFieldsToRow(int bit, List<Field> fields, boolean hasTail) {
        final List<Row> rows = new ArrayList<>();

        Row currentRow = new Row(bit);
        for (Field f : fields) {
            f = f.clone();
            while (f.getLength() != 0) {
                currentRow.addField(f);
                if (currentRow.getUsed() == bit) {
                    rows.add(currentRow);
                    currentRow = new Row(bit);
                }
            }
        }

        if (currentRow.getUsed() != 0) {
            currentRow.addTail(hasTail);
            rows.add(currentRow);
        }

        return rows;
    }

    public static class SpliceDividerService {
        public int bit;
        public List<Row> rows;

        public int index;

        public int topSegmentIndex;
        public int bottomSegmentIndex;

        public SpliceDividerService(int bit, List<Row> rows) {
            this.bit = bit;
            this.rows = new ArrayList<>(rows);

            // HACK: Fake two rows to make our life easier
            this.rows.add(0, new Row(bit).addField(new Field(null, bit)));
            this.rows.add(this.rows.size(), new Row(bit).addField(new Field(null, bit)));
        }

        public RowSegment getTopSegment() {
            return getTopRow().get(topSegmentIndex);
        }

        public RowSegment getBottomSegment() {
            return getBottomRow().get(bottomSegmentIndex);
        }

        public RowSegment getLastTopSegment() {
            return getTopRow().get(getTopRow().getCount() - 1);
        }

        public RowSegment getLastBottomSegment() {
            return getBottomRow().get(getBottomRow().getCount() - 1);
        }

        public Row getTopRow() {
            return rows.get(index);
        }

        public Row getBottomRow() {
            return rows.get(index + 1);
        }

        public boolean topRowHasNext() {
            return topSegmentIndex < getTopRow().getCount();
        }

        public boolean bottomRowHasNext() {
            return bottomSegmentIndex < getBottomRow().getCount();
        }

        public List<Divider> splice() {
            assert rows.size() >= 2;

            final List<Divider> dividers = new ArrayList<>();

            for (index = 0; index < rows.size() - 1; index++) {
                assert getTopRow().getCount() > 0;
                assert getBottomRow().getCount() > 0;

                topSegmentIndex = 0;
                bottomSegmentIndex = 0;

                Divider divider = new Divider(bit);

                while (topRowHasNext() || bottomRowHasNext()) {
                    if (topRowHasNext() && bottomRowHasNext()) {
                        if (getTopSegment().getEndIndex() < getBottomSegment().getEndIndex()) {
                            divider.addSplice(getTopSegment(), getBottomSegment());
                            topSegmentIndex++;
                        } else if (getTopSegment().getEndIndex() == getBottomSegment().getEndIndex()) {
                            divider.addSplice(getTopSegment(), getBottomSegment());
                            topSegmentIndex++;
                            bottomSegmentIndex++;
                        } else {
                            divider.addSplice(getBottomSegment(), getTopSegment());
                            bottomSegmentIndex++;
                        }
                    } else if (topRowHasNext()) {
                        divider.addSplice(getTopSegment(), getLastBottomSegment());
                        topSegmentIndex++;
                    } else {
                        divider.addSplice(getBottomSegment(), getLastTopSegment());
                        bottomSegmentIndex++;
                    }
                }
                dividers.add(divider);
            }
            return dividers;
        }
    }

    public static List<Divider> spliceDividers(int bit, List<Row> rows) {
        return new SpliceDividerService(bit, rows).splice();
    }

    public static List<Segment> mergeRowsAndDividers(List<Row> rows, List<Divider> dividers) {
        final List<Segment> segments = new ArrayList<>();

        for (int i = 0; i < rows.size(); i++) {
            segments.addAll(dividers.get(i).getSegments());
            segments.addAll(rows.get(i).getSegments());
        }

        segments.addAll(dividers.get(dividers.size() - 1).getSegments());

        return segments;
    }

    public static void setDisplayNameForAllFields(List<Segment> segments, List<Field> fields) {
        for (Field f : fields) {
            int highest = 0;
            List<Segment> related = new ArrayList<>();

            for (Segment s : segments) {
                if (f.equals(s.getRepresent())) {
                    if (highest < s.getLength()) {
                        highest = s.getLength();
                        related.clear();
                    }
                    if (highest == s.getLength()) {
                        related.add(s);
                    }
                }
            }

            if (related.size() != 0)
                related.get(related.size() / 2 - (related.size() + 1) % 2).setDisplayName(true);
        }
    }

    public static String generateHeader(List<Element> elements, int bit, String headerStyle) {
        StringBuilder sb = new StringBuilder();

        int maximumEndIndex = headerStyle.equals("full") ? bit : 0; // visible item only

        for (Element e : elements)
            if (e instanceof IVisible i && e instanceof Segment s && i.isVisible())
                maximumEndIndex = Math.max(maximumEndIndex, s.getEndIndex());

        if (maximumEndIndex == 0 || headerStyle.equals("none"))
            return "";

        for (int i = 0; i < maximumEndIndex; i++)
            sb.append(" " + (i % 10 == 0 ? i / 10 : " "));

        sb.append("\n");

        for (int i = 0; i < maximumEndIndex; i++)
            sb.append(" " + (i % 10));

        sb.append("\n");

        return sb.toString();
    }

}
