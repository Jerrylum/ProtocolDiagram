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
import com.jerryio.protocol_diagram.diagram.middlewares.LeftSpacePlaceholderVisualizer;
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
        // final int length = fields.stream().map((e) -> e.getLength()).reduce(0, (a, b) -> a + b);
        final int width = (int) this.config.getValue("bit");
        // final int height = (int) Math.ceil((double) length / (int) this.config.getValue("bit"));

        // TODO: assign/arrange field name into segment
        final var m = MarkableMatrix.create(
            // add dividers/membranes between segments
            MixedMatrix.create(
                // chunk fields into segments by row
                new Matrix(width) {{
                    for (Field f: fields) {
                        this.add(f);
                    }
                }}
            )
        );

        for (int rowidx = 0; rowidx < m.getHeight(); rowidx++) {
            final Row<IConcreteMarkable> row = m.get(rowidx);

            // top, right, bottom, left
            int flag = 0b0000;

            for (int colidx = 0, length = 0; length < row.getLength(); colidx++) {

                final var current = row.get(colidx);
                current.getName();
                current.getLength();
                current.isTextDisplay();

                if (current instanceof IMembraneDisplayable) {
                    final IMembraneDisplayable v = (IMembraneDisplayable) current;

                    // set flag
                    if (rowidx == 0) {
                        flag &= 0b0111; 
                    } else {
                        int _length = 0;
                        for (int i = 0; _length < m.get(rowidx - 1).getLength() && _length < length; i++) {
                            _length += m.get(rowidx - 1).get(i).getLength();
                        }
                        // both start is equal
                        if (length == _length) {
                            flag |= 0b1000;
                        }
                    }

                    if (rowidx == m.getHeight() - 1) {
                        flag &= 0b1101; 
                    } else {
                        int _length = 0;
                        for (int i = 0; _length < m.get(rowidx + 1).getLength() && _length < length; i++) {
                            _length += m.get(rowidx + 1).get(i).getLength();
                        }
                        // both start is equal
                        if (length == _length) {
                            flag |= 0b0010;
                        }
                    }

                    if (v.isMembraneDisplay()) flag |= 0b0100;

                    // 4 edges
                    if ((flag & 0b1111) == 0b1111) ret.append(GridType.ALL); // cross
                    // 3 edges
                    else if ((flag & 0b1110) == 0b1110) ret.append(GridType.NON_LEFT); // top right bottom
                    else if ((flag & 0b0111) == 0b0111) ret.append(GridType.NON_TOP); // left top right
                    else if ((flag & 0b1011) == 0b1011) ret.append(GridType.NON_RIGHT); // bottom left top
                    else if ((flag & 0b1101) == 0b1101) ret.append(GridType.NON_BOTTOM); // right bottom left
                    // 2 edges
                    else if ((flag & 0b1001) == 0b1001) ret.append(GridType.TOP_LEFT); // top left
                    else if ((flag & 0b1100) == 0b1100) ret.append(GridType.TOP_RIGHT); // top right
                    else if ((flag & 0b0011) == 0b0011) ret.append(GridType.BOTTOM_LEFT); // bottom left
                    else if ((flag & 0b0110) == 0b0110) ret.append(GridType.BOTTOM_RIGHT); // bottom right

                    flag = 0b0;

                    if (!current.isTextDisplay()) {
                        for (int i = 0; i < current.getLength() * 2 - 1; i++) {
                            ret.append(v.isMembraneDisplay() ? '-' : ' ');
                        }
                    } else {
                        final String str = current.getName().substring(0, Math.min(current.getLength() * 2 - 1, current.getName().length()));
                        final int test = (current.getLength() * 2 - 1 - str.length()) / 2;

                        for (int i = 0; i < test; i++) {
                            ret.append(' ');
                        }
                        for (int i = 0; i < str.length(); i++) {
                            ret.append(str.charAt(i));
                        }
                        for (int i = test + str.length(); i < current.getLength() * 2 - 1; i++) {
                            ret.append(' ');
                        }
                    }
                    if (v.isMembraneDisplay()) flag |= 0b0001;
                } else {
                    if (!current.isTextDisplay()) {
                        for (int i = 0; i < current.getLength() * 2 - 1; i++) {
                            ret.append(' ');
                        }
                    } else {
                        final String str = current.getName().substring(0, Math.min(current.getLength() * 2 - 1, current.getName().length()));
                        final int test = (current.getLength() * 2 - 1 - str.length()) / 2;

                        for (int i = 0; i < test; i++) {
                            ret.append(' ');
                        }
                        for (int i = 0; i < str.length(); i++) {
                            ret.append(str.charAt(i));
                        }
                        for (int i = test + str.length(); i < current.getLength() * 2 - 1; i++) {
                            ret.append(' ');
                        }
                    }
                }

                length += current.getLength();
            }

            if (rowidx < m.getHeight() - 1) {
                ret.append("\n");
            }
        }

        return ret.toString();
        // preprocess the list of fields
        // final Canvas canvas = new Canvas(width, height);

        // // create list of dependencies
        // final Map<Class, Object> dependencies = new HashMap<>(){{
        //     put(Configuration.class, config);
        //     put(SplitService.class, new SplitService(config));
        // }};

        // // draw header
        // HeaderStyleContext context = new HeaderStyleContext();

        // switch(this.config.getValue("header-style").toString()) {
        //     case "none": context.setStrategy(new NoneHeaderStyleStrategy(dependencies)); break;
        //     case "trim": context.setStrategy(new TrimmedHeaderStyleStrategy(dependencies)); break;
        //     case "full": context.setStrategy(new FullHeaderStyleStrategy(dependencies)); break;
        //     default: context.setStrategy(new TrimmedHeaderStyleStrategy(dependencies)); break;
        // }

        // draw canvas
        // List<IMiddleware> middlewares = new ArrayList<>() {{
        //     add(new LeftSpacePlaceholderVisualizer(dependencies));
        //     add(new BorderStyleTransformer(dependencies));
        // }};

        // for (IMiddleware middleware: middlewares) {
        //     middleware.execute(canvas, fields);
        // }

        // // append canvas
        // ret.append(context.execute(width).toString());
        // // append canvas
        // ret.append(canvas.toString());

        // // ret.append(buf);
        // return ret.toString();
    }

}
