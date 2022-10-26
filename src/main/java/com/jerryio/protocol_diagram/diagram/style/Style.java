package com.jerryio.protocol_diagram.diagram.style;

import java.util.List;

import com.jerryio.protocol_diagram.diagram.element.Corner;
import com.jerryio.protocol_diagram.diagram.element.DividerSegment;
import com.jerryio.protocol_diagram.diagram.element.Element;
import com.jerryio.protocol_diagram.diagram.element.NextLine;
import com.jerryio.protocol_diagram.diagram.element.RowSegment;
import com.jerryio.protocol_diagram.diagram.element.RowTail;

public abstract class Style {
    private List<Element> elements;

    public Style(List<Element> elements) {
        this.elements = elements;
    }

    public abstract String output(Corner e);

    public abstract String output(DividerSegment e);

    public abstract String output(RowTail e);

    public String output(NextLine e) {
        return "\n";
    }

    public String output(RowSegment e) {
        char c = 'B';

        StringBuilder sb = new StringBuilder();
        sb.append(c);
        for (int i = 1; i < e.getLength(); i++) {
            sb.append(c);
            sb.append(c);
        }
        return sb.toString();
    }

    public String output(Element e) {
        if (e instanceof Corner f)
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
            return "?";
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Element last = null;
        for (Element e : elements) {
            if (last != e) {
                sb.append(output(e));
                last = e;
            }
        }
        return sb.toString();
    }
}
