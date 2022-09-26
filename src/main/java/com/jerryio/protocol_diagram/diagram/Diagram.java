package com.jerryio.protocol_diagram.diagram;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Diagram {

    private List<Field> fields;

    public Diagram() {
        this.fields = new ArrayList<Field>();
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
        // TODO generate a diagram
        StringBuilder sb = new StringBuilder();

        for (Field field : fields) {
            sb.append(field.getName());
            sb.append("(");
            sb.append(field.getLength());
            sb.append(") ");
        }

        return sb.toString();
    }

}
