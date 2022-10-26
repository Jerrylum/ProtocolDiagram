package com.jerryio.protocol_diagram.diagram.style;

import java.util.List;

import com.jerryio.protocol_diagram.diagram.render.element.Connector;
import com.jerryio.protocol_diagram.diagram.render.element.Element;
import com.jerryio.protocol_diagram.diagram.render.element.NextLine;

public class UTF8HeaderStyle extends UTF8Style {

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
