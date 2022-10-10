package com.jerryio.protocol_diagram.diagram;

import java.util.ArrayList;
import java.util.List;

public class DiagramUtils {
    

    public static List<Row> convertFieldsToRow(int bit, List<Field> fields) {
        final List<Row> rows = new ArrayList<>();

        Row currentRow = new Row(bit);
        for (Field f : fields) {
            while (f.getLength() != 0) {
                currentRow.addField(f);
                if (currentRow.getUsed() == bit) {
                    rows.add(currentRow);
                    currentRow = new Row(bit);
                }
            }
        }

        if (currentRow.getUsed() != 0)
            rows.add(currentRow);

        rows.add(0, new Row(bit).addField(new Field(null, bit)));
        rows.add(rows.size(), new Row(bit).addField(new Field(null, bit)));

        return rows;
    }

    public static class SpliceFloorService {
        public int bit;
        public List<Row> rows;

        public int index;

        public int topSegmentIndex;
        public int bottomSegmentIndex;

        public SpliceFloorService(int bit, List<Row> rows) {
            this.bit = bit;
            this.rows = rows;
        }

        public RowSegment getTopSegment() {
            return getTopRow().getSegment(topSegmentIndex);
        }

        public RowSegment getBottomSegment() {
            return getBottomRow().getSegment(bottomSegmentIndex);
        }

        public RowSegment getLastTopSegment() {
            return getTopRow().getSegment(getTopRow().getCount() - 1);
        }

        public RowSegment getLastBottomSegment() {
            return getBottomRow().getSegment(getBottomRow().getCount() - 1);
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

        public List<Floor> splice() {
            assert rows.size() >= 2;

            final List<Floor> floors = new ArrayList<>();

            for (index = 0; index < rows.size() - 1; index++) {
                assert getTopRow().getCount() > 0;
                assert getBottomRow().getCount() > 0;

                topSegmentIndex = 0;
                bottomSegmentIndex = 0;

                Floor floor = new Floor(bit);

                while (topRowHasNext() || bottomRowHasNext()) {
                    if (topRowHasNext() && bottomRowHasNext()) {
                        if (getTopSegment().getEndIndex() < getBottomSegment().getEndIndex()) {
                            floor.addSplice(getTopSegment(), getBottomSegment());
                            topSegmentIndex++;
                        } else if (getTopSegment().getEndIndex() == getBottomSegment().getEndIndex()) {
                            floor.addSplice(getTopSegment(), getBottomSegment());
                            topSegmentIndex++;
                            bottomSegmentIndex++;
                        } else {
                            floor.addSplice(getBottomSegment(), getTopSegment());
                            bottomSegmentIndex++;
                        }
                    } else if (topRowHasNext()) {
                        floor.addSplice(getTopSegment(), getLastBottomSegment());
                        topSegmentIndex++;
                    } else {
                        floor.addSplice(getBottomSegment(), getLastTopSegment());
                        bottomSegmentIndex++;
                    }
                }
                floors.add(floor);
            }
            return floors;
        }
    }

    public static List<Floor> spliceFloors(int bit, List<Row> rows) {
        return new SpliceFloorService(bit, rows).splice();
    }

    public static List<Segment> mergeRowsAndFloors(List<Row> rows, List<Floor> floors) {
        final List<Segment> segments = new ArrayList<>();

        for (int i = 0; i < rows.size(); i++) {
            segments.addAll(rows.get(i).getSegments());
            if (i < floors.size())
                segments.addAll(floors.get(i).getSegments());
        }

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
                    related.add(s);
                }
            }

            related.get(related.size() / 2).setDisplayName(true);
        }
    }

}
