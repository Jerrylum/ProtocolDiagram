package com.jerryio.protocol_diagram.diagram;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.jerryio.protocol_diagram.config.BooleanOption;
import com.jerryio.protocol_diagram.config.Configuration;
import com.jerryio.protocol_diagram.config.EnumOption;
import com.jerryio.protocol_diagram.config.RangeOption;
import com.jerryio.protocol_diagram.diagram.middlewares.BorderCornerGenerator;
import com.jerryio.protocol_diagram.diagram.middlewares.BorderStyleTransformer;
import com.jerryio.protocol_diagram.diagram.middlewares.CollisionEliminator;
import com.jerryio.protocol_diagram.diagram.middlewares.FieldNameRender;
import com.jerryio.protocol_diagram.diagram.middlewares.IMiddleware;
import com.jerryio.protocol_diagram.diagram.middlewares.RectangleRenderer;
import com.jerryio.protocol_diagram.diagram.services.SplitService;
import com.jerryio.protocol_diagram.diagram.strategies.FullHeaderStyleStrategy;
import com.jerryio.protocol_diagram.diagram.strategies.HeaderStyleContext;
import com.jerryio.protocol_diagram.diagram.strategies.NoneHeaderStyleStrategy;
import com.jerryio.protocol_diagram.diagram.strategies.TrimmedHeaderStyleStrategy;

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
        // return value
        final StringBuilder ret = new StringBuilder();
        // canvas props
        final int length = fields.stream().map((e) -> e.getLength()).reduce(0, (a, b) -> a + b);
        final int width = Math.min(length, (int) this.config.getValue("bit"));
        final int height = (int) Math.ceil((double) length / (int) this.config.getValue("bit"));

        // preprocess the list of fields
        final Canvas canvas = new Canvas(width, height);

        // create list of dependencies
        final Map<Class, Object> dependencies = new HashMap<>(){{
            put(Configuration.class, config);
            put(SplitService.class, new SplitService(config));
        }};

        // draw on canvas
        List<IMiddleware> middlewares = new ArrayList<>() {{
            add(new RectangleRenderer(dependencies));
            add(new CollisionEliminator(dependencies));
            add(new BorderCornerGenerator(dependencies));
            add(new FieldNameRender(dependencies));
            add(new BorderStyleTransformer(dependencies));
        }};

        for (IMiddleware middleware: middlewares) {
            middleware.execute(canvas, fields);
        }

        HeaderStyleContext context = new HeaderStyleContext();

        switch(this.config.getValue("header-style").toString()) {
            case "none": context.setStrategy(new NoneHeaderStyleStrategy(dependencies)); break;
            case "trim": context.setStrategy(new TrimmedHeaderStyleStrategy(dependencies)); break;
            case "full": context.setStrategy(new FullHeaderStyleStrategy(dependencies)); break;
            default: context.setStrategy(new TrimmedHeaderStyleStrategy(dependencies)); break;
        }

        // append canvas
        ret.append(context.execute(width).toString());
        // append canvas
        ret.append(canvas.toString());

        // ret.append(buf);
        return ret.toString();
    }

}
