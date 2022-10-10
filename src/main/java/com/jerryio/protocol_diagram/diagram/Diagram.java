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
        final int bit = (int) config.getValue("bit");

        final List<Row> rows = DiagramUtils.convertFieldsToRow(bit, fields);
        final List<Floor> floors = DiagramUtils.spliceFloors(bit, rows);
        final List<Segment> segments = DiagramUtils.mergeRowsAndFloors(rows, floors);

        DiagramUtils.setDisplayNameForAllFields(segments, fields);

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
