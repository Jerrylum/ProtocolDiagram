package com.jerryio.protocol_diagram.diagram;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private List<Integer> getCollisions() {
        final List<Integer> collisions = new ArrayList<>();

        for (Field field: fields) {
            collisions.add(Math.max(field.getLength() - 32, 0));
        }

        return collisions;
    }

    private Map<Field, List<Field>> split() {
        final Map<Field, List<Field>> ret = new HashMap<>();

        for (int i = 0, length = 0; i < fields.size(); i++) {
            final Field cur = fields.get(i);
            int remain = cur.getLength();

            ret.put(cur, new ArrayList<>());

            while (length + remain > 32) {
                ret.get(cur).add(new Field(
                    cur.getName(),
                    32 - length
                ));

                remain -= 32 - length;
                length = 0;
            }

            ret.get(cur).add(new Field(
                cur.getName(),
                remain
            ));

            length = (length + remain) % 32;
        }

        return ret;
    }
    
    private String generateHeader(int width) {

        StringBuilder builder = new StringBuilder();

        // print the tens digit in the first line
        for (int i = 0; i < width; i++) {
            if (i % 10 == 0) {
                builder.append(" " + (i / 10));
            } else {
                builder.append("  ");
            }
        }

        builder.append("\n");

        // print the units digit in the second lien
        for (int i = 0; i < width; i++) {
            builder.append(" " + (i % 10));
        }

        builder.append("\n");

        return builder.toString();

    }

    @Override
    public String toString() {
        // return value
        final StringBuilder ret = new StringBuilder();
        // canvas props
        final int length = fields.stream().map((e) -> e.getLength()).reduce(0, (a, b) -> a + b);
        final int width = Math.min(length, 32);
        final int height = (int) Math.ceil((double) length / 32);

        // print header
        ret.append(generateHeader(width));

        // preprocess the list of fields
        final Canvas canvas = new Canvas(width, height);

        // draw by function
        final Map<Field, List<Field>> split = this.split();
        for (int i = 0, x = 0, y = 0; i < split.size(); i++) {
            final List<Field> chunk = split.get(this.fields.get(i));
            for (int j = 0; j < chunk.size(); j++) {
                final Field f = chunk.get(j);
                canvas.drawRectangle(x, y, f.getLength());
                y += (x + f.getLength()) / 32;
                x = (x + f.getLength()) % 32;
            }
        }

        // eliminate borders
        final List<Integer> collisions = this.getCollisions();
        for (int i = 0, x = 0, y = 0; i < fields.size() && collisions.size() != 0; i++) {
            Field f = this.fields.get(i);
            int collision = collisions.get(i);

            // eliminate border until no collision in the current context
            for (int nx = x, ny = y; collision > 0; nx = 0, ny++) {
                final int range = Math.min(collision, 32 - nx);
                canvas.eliminateBorder(nx, ny, range);
                collision -= range;
            }
            y += (x + f.getLength()) / 32;
            x = (x + f.getLength()) % 32;
        }

        // generate corners
        canvas.generateCorners();
        // append canvas
        ret.append(canvas.toString());

        // ret.append(buf);
        return ret.toString();
    }

}
