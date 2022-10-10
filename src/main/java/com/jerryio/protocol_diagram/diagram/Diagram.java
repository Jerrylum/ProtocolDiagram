package com.jerryio.protocol_diagram.diagram;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.jerryio.protocol_diagram.config.BooleanOption;
import com.jerryio.protocol_diagram.config.Configuration;
import com.jerryio.protocol_diagram.config.EnumOption;
import com.jerryio.protocol_diagram.config.RangeOption;

public class Diagram {

    private List<Field> fields;
    private Configuration config;

    public Diagram() {
        this.fields = new ArrayList<Field>();
        this.config = new Configuration(
                new RangeOption("bit", 32, 1, 128),
                new EnumOption(
                        "diagram-style", "utf8",
                        "utf8", "utf8-header", "utf8-corner", "ascii", "ascii-verbose"),
                new EnumOption(
                        "header-style", "trim",
                        "none", "trim", "full"),
                new BooleanOption("left-space-placeholder", false));
    }

    public Configuration getConfig() {
        return config;
    }

    public Collection<Field> getFields() {
        return Collections.unmodifiableCollection(fields);
    }

    public Field getField(int index) {
        return fields.get(index);
    }

    public void clear() {
        fields.clear();
    }

    public int size() {
        return fields.size();
    }

    public void addField(Field field) {
        fields.add(field);
    }

    public void insertField(int index, Field field) {
        fields.add(index, field);
    }

    public void removeField(int index) {
        fields.remove(index);
    }

    public void moveField(int from, int to) {
        Field field = fields.remove(from);
        fields.add(to, field);
    }

    @Override
    public String toString() {
        // // TODO generate a diagram
        // StringBuilder sb = new StringBuilder();

        // for (Field field : fields) {
        //     sb.append(field.getName());
        //     sb.append("(");
        //     sb.append(field.getLength());
        //     sb.append(") ");
        // }

        // return sb.toString();

        final int bit = (int) config.getValue("bit");
        final List<Row> rows = new ArrayList<>();

        int uid = 0;
        Row currentRow = new Row(bit);
        for (Field f : fields) {
            while (f.getLength() != 0) {
                currentRow.addField(uid, f);
                if (currentRow.getUsed() == bit) {
                    rows.add(currentRow);
                    currentRow = new Row(bit);
                }
            }
            uid++;
        }

        if (currentRow.getUsed() != 0)
            rows.add(currentRow);



        rows.add(0, new Row(bit).addField(uid++, new Field(null, bit)));
        rows.add(rows.size(), new Row(bit).addField(uid++, new Field(null, bit)));



        List<Floor> floors = new ArrayList<>();

        for (int i = 0; i < rows.size() - 1; i++) {           
            Row topRow = rows.get(i);
            int[] top = topRow.getSplicePositions();
            Row downRow = rows.get(i + 1);
            int[] down = downRow.getSplicePositions();
            
            Floor floor = new Floor(bit);

            // merge two list in accenting order
            int topIndex = 0, downIndex = 0;
            while (topIndex < top.length || downIndex < down.length) {
                if (topIndex < top.length && downIndex < down.length) {
                    if (top[topIndex] < down[downIndex]) {
                        floor.addSplice(-1, top[topIndex++]);
                    } else {
                        floor.addSplice(-1, down[downIndex++]);
                    }
                } else if (topIndex < top.length) {
                    floor.addSplice(-1, top[topIndex++]);
                } else {
                    floor.addSplice(-1, down[downIndex++]);
                }
            }
            floors.add(floor);
        }
        

        // System.out.println(rows.size());

        for (Row row : rows) {
            System.out.println(row.getSegments());
        }

        for (Floor floor : floors) {
            System.out.println(floor.getSegments());
        }


        return "";
    }

}
