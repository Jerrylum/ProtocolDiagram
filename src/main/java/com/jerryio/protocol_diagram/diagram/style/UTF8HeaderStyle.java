package com.jerryio.protocol_diagram.diagram.style;

import java.util.List;

import com.jerryio.protocol_diagram.diagram.render.element.Connector;
import com.jerryio.protocol_diagram.diagram.render.element.Element;
import com.jerryio.protocol_diagram.diagram.render.element.NextLine;

/**
 * this class is a extended style of UTF8, it changes the logic of rendering the first row
 * of connectors, such that the connect has a spike that goes up for indicating the index,
 * provides more readability
 */
public class UTF8HeaderStyle extends UTF8Style {
    /**
     * this constructor takes a list of elements and assigns it into private instance variable
     * 
     * @param elements a list of elements in the diagram
     */
    public UTF8HeaderStyle(List<Element> elements) {
        super(elements);
        for (Element e : elements) {
            if (e instanceof Connector f)
                f.setValue((byte) (f.getValue() | Connector.TOP));
            else if (e instanceof NextLine)
                break;
        }
    }

}
