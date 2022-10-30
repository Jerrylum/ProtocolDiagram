package com.jerryio.protocol_diagram.diagram.style;

import java.util.List;

import com.jerryio.protocol_diagram.diagram.render.element.Connector;
import com.jerryio.protocol_diagram.diagram.render.element.DividerSegment;
import com.jerryio.protocol_diagram.diagram.render.element.Element;
import com.jerryio.protocol_diagram.diagram.render.element.NextLine;
import com.jerryio.protocol_diagram.diagram.render.element.RowSegment;
import com.jerryio.protocol_diagram.diagram.render.element.RowTail;
import com.jerryio.protocol_diagram.diagram.render.element.Segment;

public abstract class Style {
    protected List<Element> elements;

    public Style(List<Element> elements) {
        this.elements = elements;
    }

    public abstract String output(Connector e);

    public abstract String output(DividerSegment e);

    public abstract String output(RowTail e);

    public String output(NextLine e) {
        return "\n";
    }

    public String output(RowSegment e) {
        return output((Segment) e);
    }

    public String output(Segment e) {
        return output(e.isDisplayName() ? e.getRepresent().getName() : " ", ' ', e.getLength());
    }

    public String output(Element e) {
        if (e instanceof Connector f)
            return output(f);
        else if (e instanceof DividerSegment f)
            return output(f);
        else if (e instanceof RowTail f)
            return output(f);
        else if (e instanceof NextLine f)
            return output(f);
        else if (e instanceof RowSegment f)
            return output(f);
        else
            return e.toString();
    }

    public String output(String name, char placeholder, int bitLength) {
        StringBuilder sb = new StringBuilder();
        int length = bitLength * 2 - 1;
        int nameLength = name.length();
        int nameIdx = (length - nameLength) / 2 + ((nameLength + 1) % 2);
        if (nameLength > length) {
            name = name.substring(0, length);
            nameIdx = 0;
        }
        for (int i = 0; i < length; i++) {
            if (i == nameIdx) {
                sb.append(name);
                i += name.length() - 1;
            } else {
                sb.append(placeholder);
            }
        }
        return sb.toString();
    }

    public String output(char odd, char even, int bitLength) {
        StringBuilder sb = new StringBuilder();
        sb.append(odd);
        for (int i = 1; i < bitLength; i++) {
            sb.append(even);
            sb.append(odd);
        }
        return sb.toString();
    }

    public String output() {
        StringBuilder sb = new StringBuilder();
        Element last = null;
        for (Element e : elements) {
            if (last != e) {
                sb.append(output(last = e));
            }
        }
        return sb.toString();
    }
}
