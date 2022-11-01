package com.jerryio.protocol_diagram.diagram.style;

import java.util.List;

import com.jerryio.protocol_diagram.diagram.render.element.DividerSegment;
import com.jerryio.protocol_diagram.diagram.render.element.Element;
import com.jerryio.protocol_diagram.diagram.render.element.Segment;

/**
 * this class is responsible in transforming the diagram into ASCII verbose style, which
 * the divider will be interleaved with + and - based on the deviation of the index
 */
public class AsciiVerboseStyle extends AsciiStyle {
    /**
     * this constructor takes a list of elements and assigns it into private instance variable
     * 
     * @param elements a list of elements in the diagram
     */
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
