package com.jerryio.protocol_diagram.diagram;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
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
import com.jerryio.protocol_diagram.diagram.render.Divider;
import com.jerryio.protocol_diagram.diagram.render.Matrix;
import com.jerryio.protocol_diagram.diagram.render.Row;
import com.jerryio.protocol_diagram.diagram.render.element.Element;
import com.jerryio.protocol_diagram.diagram.render.element.Segment;
import com.jerryio.protocol_diagram.diagram.style.AsciiStyle;
import com.jerryio.protocol_diagram.diagram.style.AsciiVerboseStyle;
import com.jerryio.protocol_diagram.diagram.style.UTF8CornerStyle;
import com.jerryio.protocol_diagram.diagram.style.UTF8HeaderStyle;
import com.jerryio.protocol_diagram.diagram.style.UTF8Style;
import com.jerryio.protocol_diagram.token.CodePointBuffer;
import com.jerryio.protocol_diagram.token.Pair;
import com.jerryio.protocol_diagram.token.Parameter;
import com.jerryio.protocol_diagram.util.DiagramUtils;

/**
 * this class holds the information of what requires to render a diagram on
 * screen, such as the list of fields and the configuration of setting.
 */
public class Diagram {

    private static final Gson GSON_BUILDER = new GsonBuilder()
            .serializeNulls()
            .registerTypeAdapter(Diagram.class, new Diagram.GsonTypeAdapter())
            .excludeFieldsWithoutExposeAnnotation()
            .create();

    /**
     * the list of fields that the diagram holds
     */
    private List<Field> fields;
    /**
     * the configuration of the diagram
     */
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

    /**
     * a getter method that returns the configuration instance of the diagram
     * 
     * @return Configuration
     */
    public Configuration getConfig() {
        return config;
    }

    /**
     * a getter method that returns a readonly clone of the list of fields of the
     * diagram
     * 
     * @return Collection
     */
    public Collection<Field> getFields() {
        return Collections.unmodifiableCollection(fields);
    }

    /**
     * a getter method that returns the field by specified index
     * 
     * @param index the index of the field
     * @return Field
     */
    public Field getField(int index) {
        return fields.get(index);
    }

    /**
     * a method that clears all of the fields of the diagram
     */
    public void clear() {
        fields.clear();
    }

    /**
     * a getter method that returns the amount of fields of the diagram
     * 
     * @return int
     */
    public int size() {
        return fields.size();
    }

    /**
     * a method that appends the field to the diagram
     * 
     * @param field the field to be appended
     */
    public void addField(Field field) {
        fields.add(field);
    }

    /**
     * a method that inserts the field into specified location to the diagram
     * 
     * @param index the location to insert
     * @param field the field to be inserted
     */
    public void insertField(int index, Field field) {
        fields.add(index, field);
    }

    /**
     * a method that removes the field via given index
     * 
     * @param index the index of the field to be removed
     */
    public void removeField(int index) {
        fields.remove(index);
    }

    /**
     * a method that moves the field from the `from` index to the `to` index
     * 
     * @param from the index of the field to be moved
     * @param to   the index of the field to be moved to
     */
    public void moveField(int from, int to) {
        Field field = fields.remove(from);
        fields.add(to, field);
    }

    /**
     * a subclass that used to record the state of the diagram, will be helpful
     * for restoring diagram via store a list of this object
     */
    public static class Memento {
        private List<Pair<String, Integer>> fields;

        private Memento(Diagram d) {
            fields = new ArrayList<>();
            for (Field f : d.getFields()) {
                fields.add(new Pair<String, Integer>(f.getName(), f.getLength()));
            }
        }
    }

    /**
     * a factory pattern that creates the `Memento` typed instance
     * 
     * @return Memento
     */
    public Memento createMemento() {
        return new Memento(this);
    }

    /**
     * a method that restores the diagram to the memento recorded state
     * 
     * @param m the memento to be restored
     */
    public void restoreFromMemento(Memento m) {
        fields.clear();
        for (Pair<String, Integer> p : m.fields) {
            fields.add(new Field(p));
        }
    }

    /**
     * a method that transforms the current diagram into a JSON formatted string
     * 
     * @return String
     */
    public String toJson() {
        return GSON_BUILDER.toJson(this);
    }

    /**
     * a utility function that creates a diagram instance based on the content of
     * the JSON formatted string
     * 
     * @param input the JSON formatted string
     * @return Diagram
     */
    public static Diagram fromJson(String input) {
        return GSON_BUILDER.fromJson(input, Diagram.class);
    }

    /**
     * a internally object representation toString method, it returns a simple
     * format of the diagram
     * 
     * @return String
     */
    @Override
    public String toString() {
        final int bit = (int) config.getValue("bit");
        final String style = (String) config.getValue("diagram-style");
        final String headerStyle = (String) config.getValue("header-style");
        final boolean leftSpacePlaceholder = (boolean) config.getValue("left-space-placeholder");

        final List<Row> rows = DiagramUtils.convertFieldsToRow(bit, fields, leftSpacePlaceholder);
        final List<Divider> dividers = DiagramUtils.spliceDividers(bit, rows);
        final List<Segment> segments = DiagramUtils.mergeRowsAndDividers(rows, dividers);
        DiagramUtils.setDisplayNameForAllFields(segments, fields);

        final Matrix matrix = new Matrix(segments);
        matrix.process();
        matrix.process(); // process twice to make sure all the connector are processed

        final List<Element> elements = matrix.getElements();

        StringBuilder sb = new StringBuilder();

        sb.append(DiagramUtils.generateHeader(elements, bit, headerStyle));

        if (style.equals("utf8"))
            sb.append(new UTF8Style(elements).output());
        else if (style.equals("utf8-header"))
            sb.append(new UTF8HeaderStyle(elements).output());
        else if (style.equals("utf8-corner"))
            sb.append(new UTF8CornerStyle(elements).output());
        else if (style.equals("ascii"))
            sb.append(new AsciiStyle(elements).output());
        else
            sb.append(new AsciiVerboseStyle(elements).output());

        return sb.toString();
    }

    /**
     * a customized gson type adapter, as the default conversion is not appropriate
     * for our diagram
     */
    public static class GsonTypeAdapter extends TypeAdapter<Diagram> {
        private static final Gson INTERNAL_GSON_BUILDER = new GsonBuilder().serializeNulls().create();

        /**
         * a method that reads JSON and returns a diagram instance
         * 
         * @param reader the reader to read JSON
         * @return Diagram
         */
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

        /**
         * a method that reads the diagram, and writes the appropriate key-value pairs
         * into the JSON
         * 
         * @param writer the writer to write the JSON
         * @param d      the diagram to be written
         */
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
