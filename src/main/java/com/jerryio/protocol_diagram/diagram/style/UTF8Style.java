package com.jerryio.protocol_diagram.diagram.style;

import java.util.List;

import com.jerryio.protocol_diagram.diagram.render.element.Connector;
import com.jerryio.protocol_diagram.diagram.render.element.DividerSegment;
import com.jerryio.protocol_diagram.diagram.render.element.Element;
import com.jerryio.protocol_diagram.diagram.render.element.RowTail;
import com.jerryio.protocol_diagram.diagram.render.element.Segment;

public class UTF8Style extends Style {

    public UTF8Style(List<Element> elements) {
        super(elements);
    }

    public String output(Connector e) {
        return new char[] {
                ' ', // 0
                ' ', // 1
                ' ', // 2
                '┐', // 3
                ' ', // 4
                '─', // 5
                '┌', // 6
                '┬', // 7
                ' ', // 8
                '┘', // 9
                '│', // 10
                '┤', // 11
                '└', // 12
                '┴', // 13
                '├', // 14
                '┼' // 15
        }[e.getValue()] + "";
    }

    @Override
    public String output(DividerSegment e) {
        if (e.isDisplayName()) {
            return output((Segment) e);
        } else {
            char c = e.isVisible() ? '─' : ' ';
            return output(c, c, e.getLength());
        }
    }

    @Override
    public String output(RowTail e) {
        if (e.isVisible())
            return output("Reserved", '─', e.getLength());
        else
            return output(' ', ' ', e.getLength());
    }

}
