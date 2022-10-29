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

    /**
     * a getter method that returns the configuration instance of the diagram
     * @return Configuration
     */
    public Configuration getConfig() {
        return config;
    }

    /**
     * a getter method that returns a readonly clone of the list of fields of the diagram
     * @return Collection<Field>
     */
    public Collection<Field> getFields() {
        return Collections.unmodifiableCollection(fields);
    }

    /**
     * a getter method that returns the field by specified index
     * @param index
     * @return Field
     */
    public Field getField(int index) {
        return fields.get(index);
    }

    /**
     * a method that clears all of the fields of the diagram
     * @return void
     */
    public void clear() {
        fields.clear();
    }

    /**
     * a getter method that returns the amount of fields of the diagram
     * @return int
     */
    public int size() {
        return fields.size();
    }

    /**
     * a method that appends the field to the diagram
     * @param field
     * @return void
     */
    public void addField(Field field) {
        fields.add(field);
    }

    /**
     * a method that inserts the field into specified location to the diagram
     * @param index
     * @param field
     * @return void
     */
    public void insertField(int index, Field field) {
        fields.add(index, field);
    }

    /**
     * a method that removes the field via given index
     * @param index
     * @return void
     */
    public void removeField(int index) {
        fields.remove(index);
    }

    /**
     * a method that move the field from the `from` index to the `to` index
     * @param from
     * @param to
     * @return void
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
     * a factory pattern that create the `Memento` typed instance
     * @return Memento
     */
    public Memento createMemento() {
        return new Memento(this);
    }

    /**
     * a method that restores the diagram to the memento recorded state
     * @param m
     * @return void
     */
    public void restoreFromMemento(Memento m) {
        fields.clear();
        for (Pair<String, Integer> p : m.fields) {
            fields.add(new Field(p));
        }
    }

    /**
     * a method that transform the current diagram into a JSON formatted string
     * @return String
     */
    public String toJson() {
        return GSON_BUILDER.toJson(this);
    }

    /**
     * a utility function that creates a diagram instance based on the content of the JSON formatted string
     * @param input
     * @return Diagram
     */
    public static Diagram fromJson(String input) {
        return GSON_BUILDER.fromJson(input, Diagram.class);
    }

    /**
     * a internally object representation toString method, it returns a simple format of the diagram
     * @return String
     */
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

    /**
     * a customized gson type adapter, as the default conversion is not appropriate for our diagram
     */
    public static class GsonTypeAdapter extends TypeAdapter<Diagram> {
        private static final Gson INTERNAL_GSON_BUILDER = new GsonBuilder().serializeNulls().create();

        /**
         * a method that read JSON and return a diagram instance
         * @param reader
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
         * a method that read the diagram, and write the appropriate key-value pairs into the JSON
         * @param writer
         * @param d
         * @return void
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
