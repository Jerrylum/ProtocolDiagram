package com.jerryio.protocol_diagram.diagram;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.jerryio.protocol_diagram.config.BooleanOption;
import com.jerryio.protocol_diagram.config.Configuration;
import com.jerryio.protocol_diagram.config.EnumOption;
import com.jerryio.protocol_diagram.config.Option;
import com.jerryio.protocol_diagram.config.RangeOption;
import com.jerryio.protocol_diagram.diagram.element.Divider;
import com.jerryio.protocol_diagram.diagram.element.Matrix;
import com.jerryio.protocol_diagram.diagram.element.Row;
import com.jerryio.protocol_diagram.diagram.element.Segment;
import com.jerryio.protocol_diagram.diagram.style.AsciiStyle;
import com.jerryio.protocol_diagram.diagram.style.AsciiVerboseStyle;
import com.jerryio.protocol_diagram.diagram.style.Style;
import com.jerryio.protocol_diagram.diagram.style.UTF8CornerStyle;
import com.jerryio.protocol_diagram.diagram.style.UTF8HeaderStyle;
import com.jerryio.protocol_diagram.diagram.style.UTF8Style;
import com.jerryio.protocol_diagram.token.CodePointBuffer;
import com.jerryio.protocol_diagram.token.Pair;
import com.jerryio.protocol_diagram.token.Parameter;

public class Diagram {

    private static final Gson GSON_BUILDER = new GsonBuilder()
            .serializeNulls()
            .registerTypeAdapter(Diagram.class, new Diagram.GsonTypeAdapter())
            .excludeFieldsWithoutExposeAnnotation()
            .create();

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

    public static class Memento {
        private List<Pair<String, Integer>> fields;

        private Memento(Diagram d) {
            fields = new ArrayList<>();
            for (Field f : d.getFields()) {
                fields.add(new Pair<String, Integer>(f.getName(), f.getLength()));
            }
        }
    }

    public Memento createMemento() {
        return new Memento(this);
    }

    public void restoreFromMemento(Memento m) {
        fields.clear();
        for (Pair<String, Integer> p : m.fields) {
            fields.add(new Field(p));
        }
    }

    public String toJson() {
        return GSON_BUILDER.toJson(this);
    }

    public static Diagram fromJson(String input) {
        return GSON_BUILDER.fromJson(input, Diagram.class);
    }

    @Override
    public String toString() {
        final int bit = (int) config.getValue("bit");

        final List<Row> rows = DiagramUtils.convertFieldsToRow(bit, fields);
        final List<Divider> dividers = DiagramUtils.spliceDividers(bit, rows);
        final List<Segment> segments = DiagramUtils.mergeRowsAndDividers(rows, dividers);
        DiagramUtils.setDisplayNameForAllFields(segments, fields);

        final Matrix matrix = new Matrix(segments);
        matrix.process();
        matrix.process(); // process twice to make sure all the corners are processed

        String style = (String) config.getOption("diagram-style").getValue();

        if (style.equals("utf8"))
            return new UTF8Style(matrix.getElements()).toString();
        else if (style.equals("utf8-header"))
            return new UTF8HeaderStyle(matrix.getElements()).toString();
        else if (style.equals("utf8-corner"))
            return new UTF8CornerStyle(matrix.getElements()).toString();
        else if (style.equals("ascii"))
            return new AsciiStyle(matrix.getElements()).toString();
        else
            return new AsciiVerboseStyle(matrix.getElements()).toString();
    }

    public static class GsonTypeAdapter extends TypeAdapter<Diagram> {
        private static final Gson INTERNAL_GSON_BUILDER = new GsonBuilder().serializeNulls().create();

        public Diagram read(JsonReader reader) throws IOException {
            Diagram d = new Diagram();

            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                if (name.equals("fields")) {
                    d.fields.addAll(Arrays.asList(INTERNAL_GSON_BUILDER.fromJson(reader, Field[].class)));
                } else if (name.equals("config")) {
                    reader.beginObject();
                    while (reader.hasNext()) {
                        String configName = reader.nextName();
                        Parameter configValue = Parameter.parse(new CodePointBuffer(reader.nextString()));
                        d.getConfig().setValue(configName, configValue);
                    }
                    reader.endObject();
                } else {
                    reader.skipValue();
                }
            }
            reader.endObject();
            return d;
        }

        public void write(JsonWriter writer, Diagram d) throws IOException {
            writer.beginObject();
            writer.name("fields").jsonValue(INTERNAL_GSON_BUILDER.toJson(d.getFields()));
            writer.name("config").beginObject();
            for (Option option : d.getConfig().getOptions()) {
                writer.name(option.getKey()).value(option.getValue().toString());
            }
            writer.endObject();
            writer.endObject();
        }
    }

}
