package com.jerryio.protocol_diagram.diagram.style;

import java.util.List;

import com.jerryio.protocol_diagram.diagram.element.Corner;
import com.jerryio.protocol_diagram.diagram.element.Element;
import com.jerryio.protocol_diagram.diagram.element.NextLine;

public class UTF8HeaderStyle extends UTF8Style {

    public UTF8HeaderStyle(List<Element> elements) {
        super(elements);
        for (Element e : elements) {
            if (e instanceof Corner f)
                f.setValue((byte) (f.getValue() | Corner.TOP));
            else if (e instanceof NextLine)
                break;
        }
    }

}
