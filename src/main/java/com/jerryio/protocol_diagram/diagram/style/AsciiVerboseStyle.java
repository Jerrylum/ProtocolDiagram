package com.jerryio.protocol_diagram.diagram.style;

import java.util.List;

import com.jerryio.protocol_diagram.diagram.render.element.DividerSegment;
import com.jerryio.protocol_diagram.diagram.render.element.Element;
import com.jerryio.protocol_diagram.diagram.render.element.Segment;

public class AsciiVerboseStyle extends AsciiStyle {

    public AsciiVerboseStyle(List<Element> elements) {
        super(elements);
    }

    @Override
    public String output(DividerSegment e) {
        if (e.isDisplayName()) {
            return output((Segment) e);
        } else {
            if (e.isVisible())
                return output('-', '+', e.getLength());
            else
                return output(' ', ' ', e.getLength());
        }
    }
    
}
